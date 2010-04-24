<?php
require 'Parser.php';
require( 'DataProvider.php');

$inPut = "before.txt";
$myFile = "after.txt";
$fi = fopen($inPut, 'w');
$fh = fopen($myFile, 'w');

$parser = new WikiSyntaxParser;
$mysqli = MySQLConnection::GetConnection();
$stmt = $mysqli->prepare("SET NAMES utf8");
$stmt->execute();
$query = "Select text from viwiki limit 0, 100";
if ($result = $mysqli->query($query))
{
	while ($row = $result->fetch_row())
	{
		$text = $row[0];
		fwrite($fi, $text."\r\n");
		$text = strip_tags($text);
		$text = preg_replace('/\|(.*)\|/is','',$text);
		$sResult = $parser->parse($text);
		$sResult = strip_tags($sResult);
		fwrite($fh, $sResult."\r\n");
	}
	$result->close();
}
$mysqli->close();
echo "Finish";
?>