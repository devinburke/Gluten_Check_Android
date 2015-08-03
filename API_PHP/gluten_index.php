<?php
$host = "localhost";
$user = "dburke";
$dbpassword = "paige905";
$database = "dburke_gluten";
$con = mysqli_connect("$host", "$user", "$dbpassword", "$database");
require_once 'include/DB_Functions.php';
if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // gets the tag posted in HashMap Params
    $tag = $_POST['tag'];
    // json response array being sent back from API
    $response = array("tag" => $tag, "error" => FALSE);
    // check for tag type
    if ($tag == 'gluten_check') {
		
		$food_name = $_POST["name"];
		$rows_not_found="Sorry! There are no items matching that tag!";
		$sql = "SELECT * FROM gluten WHERE name = '$food_name'";
		$result = mysqli_query($con, $sql);
		$num_rows = mysqli_num_rows($result);
		if ($num_rows>0){
			$rows = mysqli_fetch_array($result,MYSQLI_ASSOC);
			$response ["gluten"] = $rows["free"];
		}else{
		$response ["gluten"] = $rows_not_found;
		}
		echo json_encode($response);
		
	}
	else if ($tag == 'sub_insert'){
		
		$name  = $_POST["name"];
		$email = $_POST["email"];
		$food_name = $_POST["food_name"];
		$gluten_free = $_POST["gluten_free"];
		
		$sql = "Insert Into subs (name, email, food_name, gluten_free) VALUES ('$name', '$email', '$food_name', '$gluten_free')";
		$result = mysqli_query($con, $sql);
		$submitted = "Thank you for your contribution, you should get an email regarding whether or not your food item made it into the database";	
			
		$response ["response_sub"] = $submitted;
		echo json_encode($response);
	}
	else if ($tag == 'count'){
		
		$sql = "Select * FROM gluten";
		$result = mysqli_query($con, $sql);
		$rows = mysqli_num_rows($result);
		
		$response["count"] = $rows;
		echo json_encode($response);
	
	}
	else if ($tag == 'no'){
		
		$sql = "Select name From gluten Where free = 'no' order by name";
		$result = mysqli_query($con, $sql);
		$ar = array();
		
		
		while ($row = $result->fetch_assoc()){
			array_push($ar, $row["name"]);
		}
		
		echo json_encode($ar); 

	}
	else if ($tag = 'yes'){
		
		$sql = "Select name From gluten Where free = 'yes' order by name";
		$result = mysqli_query($con, $sql);
		$ar = array();
		while ($row = $result->fetch_assoc()){
			array_push($ar, $row["name"]);
		}
		echo json_encode($ar); 
	
	}

}
		
       
?>