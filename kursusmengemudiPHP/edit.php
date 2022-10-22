<?php
	include "koneksi.php";
	
	$id = isset($_POST['id']) ? $_POST['id'] : '';
	class emp{}
	
	if (empty($id)) { 
		echo "Error Mengambil Data id kosong"; 
		
	} else {
		$query 	= mysqli_query($con,"SELECT * FROM pesan WHERE id='".$id."'");
		$row 	= mysqli_fetch_array($query);
		
		if (!empty($row)) {
			$response = new emp();
			$response->id = $row["id"];
			$response->Nama = $row["Nama"];
			$response->Telepon = $row["Telepon"];
			$response->Tanggal_daftar = $row["Tanggal_daftar"];
			$response->Paket = $row["Paket"];
			$response->Jenis_mobil = $row["Jenis_mobil"];
			$response->Harga = $row["Harga"];
			
			echo(json_encode($response));
		} else{ 
			
			echo("Error Mengambil Data"); 
		}	
	}
?>