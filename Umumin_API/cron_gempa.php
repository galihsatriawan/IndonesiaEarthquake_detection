<?php
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_URL, 'https://antontds.com/umumin/index.php/umumin/cek_gempa');
    $result = curl_exec($ch);
    curl_close($ch);
?>