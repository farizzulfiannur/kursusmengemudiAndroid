
<?php
	
	include "koneksi.php";
	
	$id = isset($_POST['id']) ? $_POST['id'] : '';
	$Nama = isset($_POST['Nama']) ? $_POST['Nama'] : '';
	$Telepon = isset($_POST['Telepon']) ? $_POST['Telepon'] : '';
	$Tanggal_daftar = isset($_POST['Tanggal_daftar']) ? $_POST['Tanggal_daftar'] : '';
	$Paket = isset($_POST['Paket']) ? $_POST['Paket'] : '';
	$Jenis_mobil = isset($_POST['Jenis_mobil']) ? $_POST['Jenis_mobil'] : '';
	$Harga = isset($_POST['Harga']) ? $_POST['Harga'] : '';
	
	
	if (empty($Nama) || empty($Telepon) || empty($Tanggal_daftar) || empty($Paket) || empty($Jenis_mobil) || empty($Harga)) { 
		echo "Kolom isian tidak boleh kosong"; 
		
	} else if(empty($id)) {
		$query = mysqli_query($con,"INSERT INTO pesan (id,Nama,Telepon,Tanggal_daftar,Paket,Jenis_mobil,Harga) VALUES(0,'".$Nama."','".$Telepon."','".$Tanggal_daftar."','".$Paket."','".$Jenis_mobil."','".$Harga."')");
		
		if ($query) {
			echo "Data berhasil di simpan";
			
		} else{ 
			echo "Error simpan Data";
			
		}
	}else{
		$query = mysqli_query($con,"UPDATE pesan set Nama = '".$Nama."', Telepon = '".$Telepon."', Tanggal_daftar = '".$Tanggal_daftar."', Paket = '".$Paket."', Jenis_mobil = '".$Jenis_mobil."', Harga = '".$Harga."' where id = '". $id ."'");
		
		if ($query) {
			echo "Data berhasil di ubah";
			
		} else{ 
			echo "Error ubah Data";
			
		}
		
	}
?>