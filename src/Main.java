import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.*;
import javax.mail.internet.*;
class DosyaIslemleri {

    //üyelerin yazdığı dosyayı oluşturur.

    /* hocam dosyayaYazma methodunda dosya oluşturulduktan sonra yazmaya başlanınca
       içindeki yazıları siliyor bu yüzden kodu sürekli yeniden çalıştırmamanız gerekiyor.
       ben kod içinde while döngüsü yaparak size sürekli yapmak istediğiniz işlemi sordum.
       bu yüzden kodu tekrar çalıştırmak yerine tekrar işlem seçmenizi rica ediyorum ki kod
       düzgün çalışabilsin.
       kodu bu şekilde yazdım çünkü diğer türlü yazsaydım sürekli dosyaya elit üyeler ve
       genel üyeler başlığını atıcaktı bu da dosyanın okunmasını imkansız hala getirecekti. */
    public static void dosyayaYazma() throws IOException {
        File dosya1= new File("elitüyeler.txt");
        if (!dosya1.exists()){//dosya var mı diye kontrol ediyor ki sürekli yeni dosya açmasın
            dosya1.createNewFile();
        }
        FileWriter fWriter1=new FileWriter(dosya1,false);
        BufferedWriter bdWriter1=new BufferedWriter(fWriter1);
        bdWriter1.write("#ELİT ÜYELER#");
        bdWriter1.newLine();
        bdWriter1.close();

        File dosya2= new File("genelüyeler.txt");
        if (!dosya2.exists()){//dosya var mı diye kontrol ediyor
            dosya2.createNewFile();
        }
        FileWriter fWriter2=new FileWriter(dosya2,false);
        BufferedWriter bdWriter2=new BufferedWriter(fWriter2);
        bdWriter2.write("#GENEL ÜYELER#");
        bdWriter2.newLine();
        bdWriter2.close();
    }
}
class UyeEkleme{
    //bu class üye eklemek için olusturduğum class
    public static String uyeEkleme(){

        //kullanıcıdan eklenece üyenin isim soyisim ve mail bilgilerini alıyor
        //ve bu bilgileri ilgili dosyaya yazıyor
        //bu method main içinde çağırılırken hangi dosyaya yazması gerektiğiini
        //parametre olarak alıyor.

        Scanner giris= new Scanner(System.in);

        System.out.println("İsim giriniz:");
        String isim = giris.nextLine();
        System.out.println("Soysim giriniz:");
        String soyisim = giris.nextLine();
        System.out.println("Email giriniz:");
        String email = giris.nextLine();

        String bilgiler= isim+"\t"+soyisim+"\t"+email;

        return bilgiler;
    }
}
class ElitUyeEkleme extends UyeEkleme{
    public static void elitUyeEkleme(String dosya,String uyeBilgileri) throws IOException {

        File dosya3= new File(dosya);
        FileWriter fWriter3 = new FileWriter(dosya3, true);
        BufferedWriter bWriter3= new BufferedWriter(fWriter3);

        bWriter3.append(uyeBilgileri);
        bWriter3.newLine();
        bWriter3.close();

        System.out.println("Elit üye başarıyla eklendi.");
    }
}
class GenelUyeEkleme extends UyeEkleme{
    public static void genelUyeEkleme(String dosya,String uyeBilgileri) throws IOException {

        File dosya3= new File(dosya);
        FileWriter fWriter3 = new FileWriter(dosya3, true);
        BufferedWriter bWriter3= new BufferedWriter(fWriter3);

        bWriter3.append(uyeBilgileri);
        bWriter3.newLine();
        bWriter3.close();

        System.out.println("Genel üye başarıyla eklendi.");
    }
}
public class Main {
    public static void main(String[] args) throws IOException {

        boolean a=true;// while döngüsü yapıcam ki kişi eklendikten sonra mail atmaya izin verilsin.
        System.out.println("Hoşgeldiniz!");
        DosyaIslemleri.dosyayaYazma();

        while (a) {
            System.out.println("Yapmak istediğiniz işlemin numarasını şeçiniz.");
            System.out.println("1. Elit üye ekleme");
            System.out.println("2. Genel üye ekleme");
            System.out.println("3. Mail gönderme");
            System.out.println("4. Programı sonlandırma");

            Scanner giris = new Scanner(System.in);
            int secenek = giris.nextInt();

            switch (secenek) {
                case 1:
                    ElitUyeEkleme.elitUyeEkleme("elitüyeler.txt",ElitUyeEkleme.uyeEkleme());
                    break;
                case 2:
                    GenelUyeEkleme.genelUyeEkleme("genelüyeler.txt",GenelUyeEkleme.uyeEkleme());
                    break;
                case 3:mailSecme();
                    break;
                case 4:
                    System.out.println("Sistem sonlandı.");
                    a=false;
                    break;
                default:
                    System.out.println("Yanlış numara girdiniz.");
                    System.out.println("Tekrar seçim yapınız.");
            }
        }
    }
    public static void mailSecme() throws IOException {

        // method kullanıcı mail gönderme seçeneğini seçtiği zaman devreye giriyor
        // ve kullanıcıya hangi üyelere mail atmayı istediği soruluyor
        // kullanıcının istediği switch case ile kontrol ediliyor
        int secenek;
        ArrayList<String> elitMailler;
        ArrayList<String> genelMailler;

        System.out.println("Hangi üyelere mail göndermek istiyorsanız numrasını yazınız.");
        System.out.println("1. ELit üyelere mail");
        System.out.println("2. Genel üyelere mail");
        System.out.println("3. Bütün üyelere mail");
        Scanner giris= new Scanner(System.in);
        secenek= giris.nextInt();

        switch (secenek){
            case 1:
                MailAtma.Gonder(MailAdresiBulma.elitMailAdresi());
                break;
            case 2:
                MailAtma.Gonder(MailAdresiBulma.genelMailAdresi());
                break;
            case 3:
                elitMailler=MailAdresiBulma.elitMailAdresi();
                genelMailler=MailAdresiBulma.genelMailAdresi();

                //genel iki arraylist i birleştirip gonder methodunun parametresi yapıyor
                elitMailler.addAll(genelMailler);
                MailAtma.Gonder(elitMailler);
                break;
            default:
                System.out.println("Yanlış numara yazdınız. Tekrar deneyiniz.");
        }
        //gönder methodunun parametresi bir String dizisidir.
        //elitMailAdresi elit üyelerin yazdıgı mail[] dizisini
        //genelMailAdresi ise genel üyelerin yazdığı mail[] dizisini return luyor.
    }
}
class MailAdresiBulma{
    public static ArrayList<String> elitMailAdresi() throws IOException {
        File dosya=new File("elitüyeler.txt");
        FileReader fReader=new FileReader(dosya);
        BufferedReader bReader= new BufferedReader(fReader);

        String satir;
        String [] bolme;
        ArrayList<String> kelimeler = new ArrayList<>();
        ArrayList<String> mail = new ArrayList<>();

        //dosyanın sonuna gelene kadar elitüyeler dosyasını okuyor
        while ((satir=bReader.readLine())!=null){

            //okuduğu satırı aralarında tab karakteri olmasına göre bölüyor
            //böylece isim soyisim ve mail olarak bölme dizisine atıyor
            bolme= satir.split("\t",3);

            //bölme dizisini kelimeler dizisine atıyor
            kelimeler.addAll(Arrays.asList(bolme));
        }
        bReader.close();
        
        //bu for döngüsünde kelimeler dizisinden mail bilgileri çekip alıyoruz
        //kelimeler dizisinin elemanları 0=#ELİT ÜYELER# 1=isim 2=soyisim 3=mail
        //bu yüzden kelimeler dizisinin 3. indexi mail olmuş oluyor
        //sonradan eklenecek 2. kişinin mail adresi ise 6. index olduğu için
        //j değerini 3 er 3 er arttırıyoruz.
        for (int j = 3; j < kelimeler.size(); j=j+3){
            mail.add(kelimeler.get(j));
        }

        return mail;
    }
    public static ArrayList<String> genelMailAdresi() throws IOException {

        //yukarıdaki elitMailAdresi methodu ile aynı algoritmaya sahip
        //tek farkları bu method genelüyeler dosyasını okuyor.
        //eğer kullanıcı bütün üyelere mail gönder seçeneğini seçerse
        //hem elit hem de genel üyelerin mail adresine ihtiyaç duyduğumuz için
        //2 method yazdım
        File dosya=new File("genelüyeler.txt");
        FileReader fReader=new FileReader(dosya);
        BufferedReader bReader= new BufferedReader(fReader);

        String satir;
        String [] bolme;
        ArrayList<String> kelimeler = new ArrayList<>();
        ArrayList<String> mail = new ArrayList<>();

        //dosyanın sonuna gelene kadar elitüyeler dosyasını okuyor
        while ((satir=bReader.readLine())!=null){

            //okuduğu satırı aralarında tab karakteri olmasına göre bölüyor
            //böylece isim soyisim ve mail olarak bölme dizisine atıyor
            bolme= satir.split("\t",3);

            //bölme dizisini kelimeler dizisine atıyor
            kelimeler.addAll(Arrays.asList(bolme));
        }
        
        bReader.close();
        //bu for döngüsünde kelimeler dizisinden mail bilgileri çekip alıyoruz
        //kelimeler dizisinin elemanları 0=#GENEL ÜYELER# 1=isim 2=soyisim 3=mail
        //bu yüzden kelimeler dizisinin 3. indexi mail olmuş oluyor
        //sonradan eklenecek 2. kişinin mail adresi ise 6. index olduğu için
        //j değerini 3 er 3 er arttırıyoruz.
        for (int j = 3; j < kelimeler.size(); j=j+3){
            mail.add(kelimeler.get(j));
        }

        return mail;
    }
}
class KullaniciBilgi extends javax.mail.Authenticator {
    public PasswordAuthentication getPasswordAuthentication() {

        //kullanıcıdan mail adresini ve uygulama şifrelerini alıyor
        Scanner giris=new Scanner(System.in);
        System.out.println("Lütfen tekrardan mail adresinizi giriniz.");
        String username = giris.nextLine();
        System.out.println("Lütfen uygulama şifresini giriniz.");
        System.out.println("Uygulama şifrenizi gmail hesabınızdan alabilirsiniz.");
        String password = giris.nextLine();

        return new PasswordAuthentication(username, password);
    }
}
class MailAtma{
    public static void Gonder(ArrayList<String> mailAdresleri) {
        //bu method mail adreslerini parametre olarak alıyor
        //main fonksiyonun içinde çağırıldığı zaman
        //kullanıcının isteğine göre elit veya genel üyelerin
        //mail adresleri String dizisi olaak gönderiliyor

        //protokoller ile ilgili özellikler tanımlanıyor
        //ben gmail kullandığım için bu şekilde tanımladım
        Properties ozellik = new Properties();
        ozellik.put("mail.transport.protocol", "smtp");
        ozellik.put("mail.smtp.host", "smtp.gmail.com");
        ozellik.put("mail.smtp.port", "587");
        ozellik.put("mail.smtp.auth", "true");
        ozellik.put("mail.smtp.starttls.enable", "true");
        ozellik.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

        KullaniciBilgi kullaniciBilgi = new KullaniciBilgi();
        Session mailSession = Session.getDefaultInstance(ozellik, kullaniciBilgi);

        try {
            Transport transport = mailSession.getTransport();

            MimeMessage mail = new MimeMessage(mailSession);
            Scanner giris=new Scanner(System.in);

            System.out.println("Lütfen atmak istediğiniz maili giriniz.");
            mail.setContent(giris.nextLine(), "text/plain");
            System.out.println("Lütfen mail adresinizi giriniz.");
            mail.setFrom(new InternetAddress(giris.nextLine()));

            for(Object to: mailAdresleri){
                //tek tek her adrese mail atılıyor
                mail.addRecipient(Message.RecipientType.TO, new InternetAddress((String) to));
                transport.connect();
                transport.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
                transport.close();
            }
            System.out.println("Mail başarıyla gönderildi.");

        } catch (Exception hata) {
            System.out.println(hata.getMessage());
        }
    }
}