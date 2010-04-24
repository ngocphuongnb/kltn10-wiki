<?php
/**
 *
 *
 */
class MySQLConnection{
	/**
	 * Constructor
	 * @access protected
	 */
	public function GetConnection(){
		$mysqli = new mysqli("localhost", "root", "", "kltn");
		if (mysqli_connect_errno()) {
			printf("Connect failed: %s\n", mysqli_connect_error());
			exit();
		}
		return $mysqli;
	}
}
?>