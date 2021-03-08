

package cn.com.dhcc.license.client.util.server;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Properties;

import cn.com.dhcc.license.client.util.license.LicenseEntity;






public class LicenseRevert {
    public static void main(String[] args) throws IOException, ParseException {
        if (args.length == 0) {
            System.err.println("file path is null");
            return;
        }
        File file = new File(args[0]);
        if (file.isFile()) {
            createLicense(new FileInputStream(file));
        } else {
            System.err.println("file " + file.getAbsolutePath() + " is not exists");
        }
    }

    private static void createLicense(InputStream inputStream) throws IOException, ParseException {
        Properties properties = new Properties();
        properties.load(inputStream);
        String expireDate = properties.getProperty(SourceLicense.PROPERTY_EXPIRE_DATE);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] content = decoder.decode(properties.getProperty(SourceLicense.PROPERTY_BASE64_CONTENT));
        byte[] publicKey = decoder.decode(properties.getProperty(SourceLicense.PROPERTY_PUBLIC_KEY));
        byte[] privateKey = decoder.decode(properties.getProperty(SourceLicense.PROPERTY_PRIVATE_KEY));
        SimpleDateFormat sdf = new SimpleDateFormat(SourceLicense.DATE_FORMAT);
        LicenseEntity licenseEntity = new LicenseEntity(sdf.parse(expireDate).getTime(), publicKey, md5(content));
        licenseEntity.setData(content);

        //生成License
        File file = new File("license_revert.txt");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            LicenseEncode licenseEncode = new LicenseEncode();
            fileOutputStream.write(Base64.getEncoder().encodeToString(licenseEncode.encode(licenseEntity, privateKey)).getBytes(StandardCharsets.UTF_8));
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }
    
    public static String md5(byte[] data) {
        final int m = 2;
        final int n = 4;
        final int a = 0xf;
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(data);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * m];
            int k = 0;
            byte byte0 = 0;
            for (int i = 0; i < j; i++) {
                byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> n & a];
                str[k++] = hexDigits[byte0 & a];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}
