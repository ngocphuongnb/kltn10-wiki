<?php

class WikiSyntaxParser
{
	protected $text, $debug, $image_extensions=array('png', 'jpg', 'gif');

	protected $escape_chars = array('\{', '\}', '\*', '\_', '\`', '\^', '\,,', '\~~', '\[', '\]', '\(', '\)');
	protected $escape_chars_entities = array('{', '}', '*', '_', '\'', '^', ',,', '~~', '[', ']', '(', ')');

	protected $hashes = array('hash'=>array(), 'html'=>array());

	// Tags
	protected $tags = array(
	    'hr' => '<hr />'
	    );

	// Function execution order
	protected $functions = array(
	    'make_safe',
	    'hash_code_blocks',
	    'escape_chars',
	    'hash_links',
	    'hash_urls',
	    'parse_headers',
	    'parse_typeface',
	    'parse_divider',
	    'parse_lists',
	    'parse_quotes',
	    'handle_whitespaces',
	    'replace_hashes'
	    );

	public function __construct()
	{

	}

	public function parse($text)
	{
	    // Set input to class var
	    $this->text = $text;

	    // Execute functions
	    foreach ($this->functions as $function)
	        $this->text = $this->{$function}($this->text);

	    // Add paragraph tag around text, but make sure there are no empty paragraphs
	    $this->text = preg_replace('#<br />\s*?<br />((\s*<br />)*)#is', "</p>$1<p>", $this->text);
	$this->text = str_replace('<p><br />', '<p>', $this->text);
	$this->text = str_replace('<p></p>', '', '<p>'.$this->text.'</p>');

	return $this->text;
}

/* --- Prepare and cleanup functions
   ------------------------------------------------- */

protected function make_safe($text)
{
	return preg_replace('/^[ ]+$/m', '', self::html_encode(self::linebreaks($text)));
}

protected function escape_chars($text)
{
	return str_replace($this->escape_chars, $this->escape_chars_entities, $text);
}

protected function handle_whitespaces($text)
{
	return str_replace(array("\n", "\t", '  ', '  '), array('<br />', '&nbsp; &nbsp; ', '&nbsp; ', ' &nbsp;'), $text);
}

protected function replace_hashes($text)
{
	if (count($this->hashes['hash']) > 0)
		return str_replace($this->hashes['hash'], $this->hashes['html'], $text);
	else
		return $text;
}

/* --- Hash functions
   ------------------------------------------------- */

protected function hash_code_blocks($text)
{
	$pattern = '/(\{{3})(.*?)(\}{3})/ims';
	return preg_replace_callback($pattern, array(&$this, 'cb_hash_code_blocks'), $text);
}

// Makes hashes of links
protected function hash_links($text)
{
	$pattern = '/\[(\((rel|class|id)\=(.*?)\))*((http|https|ftp|irc):\/\/[a-zA-Z0-9._%-?&\/:\#]+)[ ](.*?)\]/i';
	return preg_replace_callback($pattern, array(&$this, 'cb_hash_links'), $text);
}

// Makes hashes of urls
protected function hash_urls($text)
{
	$pattern = '/(\((rel|class|id)\=(.*?)\))*((http|https|ftp|irc):\/\/[a-zA-Z0-9._%-?&\/:\#]+)/i';
	return preg_replace_callback($pattern, array(&$this, 'cb_hash_urls'), $text);
}

/* --- Parse functions
   ------------------------------------------------- */

protected function parse_headers($text)
{
	return preg_replace_callback('/^(\={1,6})[ ]*(.*?)[ ]*\=*\n+/m', array(&$this, 'cb_parse_headers'), $text);
}

protected function parse_typeface($text)
{
	// Patterns
	$patterns = array(
	    '/\*(.*?)\*/i',
	    '/_(.*?)_/i',
	    '/`(.*?)`/i',
	    '/\^(.*?)\^/i',
	    '/,,(.*?),,/i',
	    '/~~(.*?)~~/i',
	    );

	// Replacements
	$replacements = array(
	    '<strong>$1</strong>',
	    '<em>$1</em>',
	    '<code>$1</code>',
	    '<sup>$1</sup>',
	    '<sub>$1</sub>',
	    '<span style="text-decoration: line-through;">$1</span>',
	    );

	return preg_replace($patterns, $replacements, $text);
}

protected function parse_divider($text)
{
	return preg_replace('/^(\-{4,})\n+/m', '</p>'.$this->tags['hr'].'<p>', $text);
}

protected function parse_quotes($text)
{
	return preg_replace_callback('/([ ]{4,}|[\t]{1,})(.*?).+\n(.+\n)*\n*\n+/m', array(&$this, 'cb_parse_quotes'), $text); // ~_~
}

protected function parse_lists($text)
{
	return preg_replace_callback('/([ ]{1})([*#]+)[ ](.*?)(.+\n)*/m', array(&$this, 'cb_parse_lists'), $text);
}

/* --- Callback functions
   ------------------------------------------------- */

protected function cb_parse_headers($matches)
{
	$level = strlen($matches[1]);
	return '</p><h'.$level.'>'.$matches[2].'</h'.$level.'><p>';
}

protected function cb_hash_code_blocks($matches)
{
	$code_block_hash = md5($matches[0].mt_rand());
	$attribute = NULL;

	$this->hashes['hash'][] = $code_block_hash;
	$this->hashes['html'][] = '</p><pre><code>'.trim(str_replace("\t", '&nbsp; &nbsp; ', $matches[2]), "\n").'</code></pre><p>';

	return $code_block_hash;
}

protected function cb_hash_links($matches)
{
	$link_hash = md5($matches[0].mt_rand());
	$attribute = NULL;

	$this->hashes['hash'][] = $link_hash;

	if (!empty($matches[2]))
		$attribute = ' '.$matches[2].'="'.$matches[3].'"';

	if (!in_array(self::get_ext($matches[6]), $this->image_extensions))
		$this->hashes['html'][] = '<a href="'.$matches[4].'" title="'.$matches[6].'"'.$attribute.'>'.$matches[6].'</a>';
	else
		$this->hashes['html'][] = '<a href="'.$matches[4].'"'.$attribute.'><img src="'.$matches[6].'" alt="" /></a>';

	return $link_hash;
}

protected function cb_hash_urls($matches)
{
	$url_hash = md5($matches[0].mt_rand());
	$attribute = NULL;

	$this->hashes['hash'][] = $url_hash;

	if (!empty($matches[2]))
		$attribute = ' '.$matches[2].'="'.$matches[3].'"';

	if (!in_array(self::get_ext($matches[4]), $this->image_extensions))
		$this->hashes['html'][] = '<a href="'.$matches[4].'"'.$attribute.'>'.$matches[4].'</a>';
	else
		$this->hashes['html'][] = '<img src="'.$matches[4].'" alt=""'.$attribute.' />';

	return $url_hash;
}

protected function cb_parse_quotes($matches)
{
	$temp_lines = explode("\n", $matches[0]);

	foreach ($temp_lines as $line)
		$lines[] = trim($line);

	return '</p><blockquote><p>'.implode("\n", $lines).'</p></blockquote><p>';
}

// NOTICE: Nesting lists is currently not possible, because it's breaking my head. O_O
protected function cb_parse_lists($matches)
{
	$items = explode("\n", trim($matches[0], "\t\n"));
	$count_items = count($items);

	for ($x=0;$x < $count_items;$x++)
	{
		// Items
		$cur_item = $items[$x];
		$cur_item_trim = trim($cur_item, "* #");
		$next_item = ($x+1) < $count_items ? $items[$x+1] : false;

		if (preg_match('/([ ]*)([*#]+)[ ](.*?)/i', $cur_item, $m))
		{
			$cur_item_len = strlen($m[1]);

			$next_item_len = 0;
			$list_el = $m[2] == '*' ? 'ul' : 'ol';

			if (preg_match('/([ ]*)([*#]+)[ ](.*?)/i', $next_item, $nm))
				$next_item_len = strlen($nm[1]);

			if (!isset($lists[$cur_item_len]))
			{
				$lists[$cur_item_len] = $cur_item_len;
				$line = '<'.$list_el.'><li>'.$cur_item_trim;
			}
			else
			{
				$line = '<li>'.$cur_item_trim;
			}

			if($next_item_len <= $cur_item_len)
				$line .= '</li>';

			foreach (array_reverse($lists) as $k => $v)
			{
				$result[] = $v;

				if($v > $next_item_len)
				{
					$line .= '</'.$list_el.'>';

					if($v > 1)
						$line .= '</li>';

					unset($lists[$v]);
				}
			}
		}

		$output[] = $line;
	}

	return '</p>'.implode($output).'<p>';
	// return '</p>'.implode($output).'<pre>'.print_r($result, true).'</pre><p>';
	// return '</p><pre>'.self::html_encode(print_r($output, true)).'</pre><pre>'.print_r($result, true).'</pre><p>';
}

/* --- String manipulation functions
   ------------------------------------------------- */

// Encode html
static protected function html_encode($str)
{
	return htmlspecialchars(trim($str), ENT_QUOTES, 'UTF-8');
}

// Convert linebreakes to unix linebreaks
static protected function linebreaks($str)
{
	return str_replace(array("\r\n", "\r"), "\n", $str);
}

// Get extension from image
static protected function get_ext($filepath)
{
	$explode = explode('.', $filepath);

	return $explode[count($explode)-1];
}
}

?>