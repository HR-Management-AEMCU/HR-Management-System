# Docker a postgres kurmak

 ## 1. terminalde yazılacak, bu gerekli dosyaları docker a indirecek
    docker pull postgres 
 ## 2. inirmeden sonra
    docker run --name HRMSPostgres -e POSTGRES_PASSWORD=root -p 9999:5432 postgres
  bu işlem docerda containers yarattı 
 ## 3. postgresql arayüzüne dockerdaki psql li bağlamak
  pg admin açılıp yeni server eklenecek. sonra sekmelerde: general->name

    DockerHRMSPostgres
Connection->host

    localhost
Connection->port 

    9999
Connection->port

    root
sonra database oluşturulacak adı 

    HRMSCompanyDB
Not: Muhammet hoca dersi 4 nisan saat 2.30

    mongodb:
      host: localhost
      port: 27017
      database: HR-ManagementDB
      username: JavaUser
      password: root



mail service için env dataları
    
    mailadresi=bertanjava7@gmail.com;mailsifrem=xsrkurhceqzcpgde

