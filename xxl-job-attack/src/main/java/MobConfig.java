//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import java.io.Console;
import java.io.File;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MobConfig {
    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int KEY_LENGTH = 128;
    private static final String IV_PARAMETER = "Ac4D83J%F8DWDDhu";
    private static final char[] DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public MobConfig() {
    }

    private static int getDBType(Scanner sc) {
        System.out.println("\n请选择数据库类型：1-MySQL,2-Oracle,3-SQL,Server,4-IBM DB2");
        if (sc.hasNextInt()) {
            int iType = sc.nextInt();
            return iType > 0 && iType < 5 ? iType : getDBType(sc);
        } else {
            sc.next();
            return getDBType(sc);
        }
    }

    private static String getDBIP(Scanner sc) {
        System.out.println("\n请输入数据库服务器的地址(ip)：");
        return sc.next().trim();
    }

    private static int getDBNet(Scanner sc) {
        System.out.println("\n请输入数据库服务器端口(port)：");
        if (sc.hasNextInt()) {
            int iDBNet = sc.nextInt();
            return iDBNet;
        } else {
            sc.next();
            return getDBNet(sc);
        }
    }

    private static String getDBName(Scanner sc) {
        System.out.println("\n请输入数据库的名称(db name)：");
        return sc.next().trim();
    }

    private static String getUserName(Scanner sc) {
        System.out.println("\n请输入访问数据库的用户名(user)：");
        return sc.next().trim();
    }

    private static char[] getPwd(Scanner sc, String userName) {
        Console cons = System.console();
        char[] pwd = cons.readPassword("\n请输入用户" + userName + "的密码(password): ", new Object[0]);
        char[] pwdRetry = cons.readPassword("请再次输入用户" + userName + "的密码(password): ", new Object[0]);
        if (Arrays.equals(pwd, pwdRetry)) {
            return pwd;
        } else {
            System.out.println("\n两次输入的密码不一致，请重新输入。");
            return getPwd(sc, userName);
        }
    }

    private static char[] getPwdIDE(Scanner sc, String userName) {
        System.out.println("\n请输入用户" + userName + "的密码(password): ");
        return sc.next().trim().toCharArray();
    }

    private static byte[] initKey(byte[] pwd) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(pwd);
        kg.init(128, random);
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }

    public static byte[] encrypt(byte[] data, byte[] pwd) throws Exception {
        byte[] key = initKey(pwd);
        SecretKey k = new SecretKeySpec(key, "AES");
        IvParameterSpec iv = new IvParameterSpec("Ac4D83J%F8DWDDhu".getBytes());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, k, iv);
        return cipher.doFinal(data);
    }

    private static byte[] decrypt(byte[] data, byte[] pwd) throws Exception {
        byte[] key = initKey(pwd);
        SecretKey k = new SecretKeySpec(key, "AES");
        IvParameterSpec iv = new IvParameterSpec("Ac4D83J%F8DWDDhu".getBytes());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, k, iv);
        return cipher.doFinal(data);
    }

    private static String encodeDBPwd(String dbName, String userName, char[] dbPwd) throws Exception {
        char[] fix = new char[]{'a', '#', 'd', '5', 'F', 'K', '*', '9', 'X', 'y', '|'};
        byte[] pwd = (dbName + "|" + userName + "|" + new String(fix)).getBytes();
        byte[] encodeBuf = encrypt((new String(dbPwd)).getBytes(), pwd);
        return new String(encodeHex(encodeBuf));
    }

    public static char[] decodeDBPwd(String dbName, String userName, String encodeDBPwd) throws Exception {
        char[] fix = new char[]{'a', '#', 'd', '5', 'F', 'K', '*', '9', 'X', 'y', '|'};
        byte[] pwd = (dbName + "|" + userName + "|" + new String(fix)).getBytes();
        byte[] data = decodeHex(encodeDBPwd.toCharArray());
        byte[] decodeBuf = decrypt(data, pwd);
        return (new String(decodeBuf)).toCharArray();
    }

    private static byte[] decodeHex(char[] data) throws Exception {
        int len = data.length;
        if ((len & 1) != 0) {
            throw new Exception("Odd number of characters.");
        } else {
            byte[] out = new byte[len >> 1];
            int i = 0;

            for(int j = 0; j < len; ++i) {
                int f = toDigit(data[j], j) << 4;
                ++j;
                f |= toDigit(data[j], j);
                ++j;
                out[i] = (byte)(f & 255);
            }

            return out;
        }
    }

    protected static int toDigit(char ch, int index) throws Exception {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new Exception("Illegal hexadecimal charcter " + ch + " at index " + index);
        } else {
            return digit;
        }
    }

    private static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        int i = 0;

        for(int var4 = 0; i < l; ++i) {
            out[var4++] = DIGITS[(240 & data[i]) >>> 4];
            out[var4++] = DIGITS[15 & data[i]];
        }

        return out;
    }

    private static String formatPath(String path) {
        path = path.replace('\\', File.separatorChar);
        path = path.replace('/', File.separatorChar);
        int length = path.length();

        for(char endChar = path.charAt(length - 1); File.separatorChar == endChar; endChar = path.charAt(length - 1)) {
            path = path.substring(0, length - 1);
            length = path.length();
        }

        return path;
    }

    private static boolean initPropertiesFile(String pathFileName, Scanner sc) throws Exception {
        File pathFile = new File(pathFileName);
        String pathName;
        if (pathFile.exists() && pathFile.isFile()) {
            System.out.println("\n文件已存在是否覆盖？(file alredy exist.overwrite?)：y/n");
            pathName = sc.next().trim();
            if (!"y".equals(pathName)) {
                return false;
            }

            String bakPathFileName = pathFileName + ".bak";
            File bakPathFile = new File(bakPathFileName);
            bakPathFile.delete();
            if (!pathFile.renameTo(new File(bakPathFileName))) {
                System.out.println("备份文件失败。");
                return false;
            }
        }

        pathName = pathFile.getParent();
        if (pathName != null && pathName.length() > 0) {
            File path = new File(pathName);
            if (!path.exists() || !path.isDirectory()) {
                path.mkdirs();
            }
        }

        return pathFile.createNewFile();
    }

    public static void main(String[] args) throws Exception {

        String username = "ydpt";
        String dbname = "ghncdb";
        char[] decodepwd = MobConfig.decodeDBPwd(dbname, username, "67173878e8badf5179d97a6071e164a6");
        if (decodepwd != null && decodepwd.length > 0) {
            System.out.println(decodepwd);
        }

    }
}
