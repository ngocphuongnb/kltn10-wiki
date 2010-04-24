<?php
require 'Parser.php';
require( 'DataProvider.php');

$myFile = "testFile.txt";
$fh = fopen($myFile, 'w');


$parser = new WikiSyntaxParser;
$mysqli = MySQLConnection::GetConnection();
$stmt = $mysqli->prepare("SET NAMES utf8");
$stmt->execute();
$query = "Select text from viwiki limit 0, 10";
if ($result = $mysqli->query($query))
{
	while ($row = $result->fetch_row())
	{
		$text = $row[0];
		//$text = strip_tags($text);
				echo $text."<br>";
		$text = preg_replace('/\{\|(.*)\|\}/is','',$text); 
		//$sResult = $parser->parse($text);
		//$sResult = strip_tags($sResult);
		//fwrite($fh, $sResult . "\r\n");
//		echo $sResult."<br>";

	}
	$result->close();
}
$mysqli->close();

echo "Hoàn t?t";
?>