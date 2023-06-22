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

AuthService --> 8090
CompanyService --> 8070
UserService --> 8060
MailService--> 8085

        KUBERNETES
PgAdmin-> Servers -> Register -> Server

Name:HRMSKubernetesServer

Connection
Host name: 34.163.33.125
Port: 22222
Password:root
---------------------
MongoDB Compass
New Connection
Host -> 34.163.221.243:21017

Authentication
Username : HRMSMongoDB
Password : root


### 7.1 Dockerfile

    Dockerfile bir docker image oluşturmak için kullanılan özel 
    bir dosyadır. bunun içinde ihtiyaç duyulan tüm komutlar yer alır.
    bu komutlar ile microservisimizin çalışması için gerekli olan 
    parametreler ve bağımlılıklar belirtilir.
````
    # İşletim sistemi ve Java JDK eklenir.
    # FROM amazoncorretto:17 -- amazon corretto ile java jdk17 sürümü kullanılacak demektir.
    FROM azul/zulu-openjdk-alpine:17.0.7
    # build aldığımız jar dosyasını docker imajımızın içine kopyalıyoruz.
    COPY ConfigServerGit/build/libs/ConfigServerGit-v.0.0.1.jar app.jar
    # docker imajımızın çalışması için java uygulamamızı tetikliyoruz.
    ENTRYPOINT ["java","-jar","/app.jar"]
````
## Docker Imaje oluşturmak
    DİKKAT!!!!
    Docker image oluştururken docker.hub üzerindeki repoya önderilmek istenilen 
    image lar için isimlendirmeyi doğru yapmanız gereklidir. 
    hub.docker repo adınız / image adınız : versiyon numarası
    şeklinde yazmanız gereklidir.
    DİKKAT!!!! 
    ifade sonunda olan nokta (.) unutulmamalıdır. rastgele bir nokta değildir.
    docker build -t unzilemedet/hrms-configservergit:v.1.0 .
  
