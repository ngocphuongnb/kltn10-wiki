<?php

// make sure browsers see this page as utf-8 encoded HTML
header('Content-Type: text/html; charset=utf-8');

$limit = 10;
$currentpage = 1;
$query = isset($_REQUEST['q']) ? $_REQUEST['q'] : false;
$results = false;

echo $query."<br>";

if (isset($_REQUEST["page"]))
	$currentpage = $_REQUEST["page"];

if ($query)
{
  // The Apache Solr Client library should be on the include path
  // which is usually most easily accomplished by placing in the
  // same directory as this script ( . or current directory is a default
  // php include path entry in the php.ini)
  require_once('Apache/Solr/Service.php');

  // create a new solr service instance - host, port, and webapp
  // path (all defaults in this example)
  $solr = new Apache_Solr_Service('localhost', 8983, '/solr/');

  // if magic quotes is enabled then stripslashes will be needed
  if (get_magic_quotes_gpc() == 1)
  {
  echo $query."<br>";
    $query = stripslashes($query);
	echo $query."<br>";
  }

  // in production code you'll always want to use a try /catch for any
  // possible exceptions emitted  by searching (i.e. connection
  // problems or a query parsing error)
  try
  {
	$start = ($currentpage-1) * $limit;
    $results = $solr->search($query, $start, $limit);
  }
  catch (Exception $e)
  {
    // in production you'd probably log or email this error to an admin
        // and then show a special message to the user but for this example
        // we're going to show the full exception
        die("<html><head><title>SEARCH EXCEPTION</title><body><pre>{$e->__toString()}</pre></body></html>");
  }
}

?>
<html>
  <head>
    <title>PHP Solr Client Example</title>
  </head>
  <body>
    <form  accept-charset="utf-8" method="get">
      <label for="q">Search:</label>
      <input id="q" name="q" type="text" value="<?php echo htmlspecialchars($query, ENT_QUOTES, 'utf-8'); ?>"/>
      <input type="submit"/>
    </form>
<?php

// display results
if ($results)
{
  $total = (int) $results->response->numFound;
  $end = min($start+$limit, $total);
?>
    <div>Results <?php echo $start+1; ?> - <?php echo $end;?> of <?php echo $total; ?>:</div>
    <ol>
<?php
  // iterate result documents
  foreach ($results->response->docs as $doc)
  {
?>
      <li>
        <table style="border: 1px solid black; text-align: left">
<?php
    // iterate document fields / values
    foreach ($doc as $field => $value)
    {
?>
          <tr>
            <th><?php echo htmlspecialchars($field, ENT_NOQUOTES, 'utf-8'); ?></th>
            <td><?php echo htmlspecialchars($value, ENT_NOQUOTES, 'utf-8'); ?></td>
          </tr>
<?php
    }
?>
        </table>
      </li>
<?php
  }
?>
    </ol>
<?php
}

$numpage = ceil($total / ($limit*1.0));
if(isset($query)) // Neu da co query
	echo "Number of page is: $numpage <br/>";
$self = $_SERVER['PHP_SELF'];
$nav = "";

for($i = 1; $i <= $numpage; $i ++)
{
	if ($i == $currentpage)
		$nav .= $i;
	else
		$nav .= "<a href=\"$self?q=$query&page=$i\">$i</a> "; 
}

if($numpage>1) // Neu co nhieu hon 1 trang
	echo $nav;
?>
  </body>
</html>