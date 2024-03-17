package com.capstone.chillgoapp.model

import com.capstone.chillgoapp.R

object FakeTravelDataSource {

    val dummyTravels = listOf(
        Travel(
            1, R.drawable.lembang_park_and_zoo, "Lembang Park And Zoo",
            "Kalau kamu ingin berkunjung ke kebun binatang dengan gaya modern, Lembang Park and Zoo tempatnya. Berdiri di lahan seluas 25 hektar, ada sekitar 350 ekor binatang yang terdiri dari pisces, reptilia, aves hingga mamalia",
            50000
        ),
        Travel(
            2, R.drawable.thegreat_asiaafrika, "The Great Asia Afrika",
            "The Great Asia Afrika menawarkan sensasi berkeliling ke tujuh negara. Jadi, wisatawan bisa melihat replika bangunan dari negara India, Korea Selatan, Jepang hingga Timur Tengah.",
            50000
        ),
        Travel(
            3, R.drawable.lembang_wonderland, "Lembang Wonderland",
            "Lembang Wonderland menjadi tempat wisata yang menyerupai negeri dongeng dengan area berwarna-warni. Konsep yang diusung adalah taman rekreasi Glow in The Dark, sebab, saat malam hari gemerlap lampu warna-warni yang begitu cantik.",
            35000
        ),
        Travel(
            4, R.drawable.panama_park, "Panama Park",
            "Panama Park disebut-sebut menjadi wahana rekreasi terbesar di Bandung. Beberapa permainan yang ditawarkan mulai dari playground hingga snow park yang berisi salju.",
            50000
        ),
        Travel(
            5, R.drawable.farmhouse_lembang, "Farmhouse Lembang",
            "Liburan bernuansa Eropa bisa kamu dapatkan di Farmhouse Lembang. Seperti The Great Asia Afrika, destinasi ini juga menyewakan kostum yang bisa disewa untuk pengunjung.",
            35000
        ),
        Travel(
            6, R.drawable.floating_market_lembang, "Floating Market",
            "Floating Market merupakan wisata kuliner yang mengusung konsep perahu apung. Wisatawan bisa menikmati berbagai jenis makanan dan minuman dengan pemandangan danau.",
            40000
        ),
        Travel(
            7, R.drawable.dago_dream_park, "Dago Dream Park",
            "Dago Dream Park memiliki nuansa pegunungan dengan pemandangan pohon pinus yang asri dan menyejukkan. Luasnya mencapai 13 hektar.Beberapa wahananya yaitu sky gliding atau flying fox, wall climbing, paintball, pertandingan tembak-menembak hingga Tarzan cross yang mengajakmu melewati jalur di atas ketinggian. Selain itu ada wahana selfie seperti up house, aladdin carpet hingga tinkerbell wings.",
            30000
        ),
        Travel(
            8, R.drawable.grafika_cikole, "Grafika Cikole",
            "Kawasan ini menyuguhkan berbagai wahana permainan mulai dari flying fox, flying board dan masih banyak lagi. Lokasinya berada di Jalan Tangkuban Perahu KM.8, Cikole, Lembang, Bandung Barat. Tiket masuknya adalah Rp 20.000.",
            20000
        ),
        Travel(
            9, R.drawable.trans_studio_bandung, "Trans Studio Bandung",
            "Wisata indoor juga bisa menjadi pilihan untuk berwisata bersama keluarga. Trans Studio Bandung memiliki banyak wahana, mulai dari yang mengedukasi hingga menantang adrenalin.",
            300000
        ),
        Travel(
            10, R.drawable.bukit_bintang_bandung, "Bukit Bintang",
            "Bukit bintang cocok untuk traveler yang suka berfoto-foto dengan latar pemandangan alam. Memiliki luas 12,20 hektar, bukit bintang memiliki hutan pinus dan jalan setapak.",
            15000
        ),
        Travel(
            11, R.drawable.tangkuban_parahu, "Tangkuban Perahu",
            "Tangkuban Perahu atau Tangkuban Parahu merupakan salah satu destinasi wisata yang banyak dikunjungi oleh masyarakat, baik turis lokal maupun asing. Dikutip dari situs Indonesai Kaya, gunung ini terletak di ketinggian 2.048 mdpl dengan suhu normalnya sekitar 17 derajat Celcius. Udanya sangat dingin sehingga pengunjung disarankan menggunakan jaket dan celana panjang.",
            35000
        ),
    )

    val dummyBestTravel = dummyTravels.shuffled()
}