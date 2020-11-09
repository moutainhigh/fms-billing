package org.fms.billing.common.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.fms.billing.common.webapp.entity.MapEntity;

public class FormatterUtil {

    public static Map<Long, String> ListMapToMap(List<Map<Long, Object>> listMap) {
        Map<Long, String> map = new HashMap<>();

        if (listMap != null && !listMap.isEmpty()) {
            for (Map<Long, Object> map1 : listMap) {
                Long key = null;
                String value = null;
                for (Map.Entry<Long, Object> entry : map1.entrySet()) {
                    if ("key".equals(entry.getKey())) {
                        key = (Long) entry.getValue();
                    } else if ("value".equals(entry.getKey())) {
                        value = (String) entry.getValue();
                    }
                }
                map.put(key, value);
            }
        }
        return map;

    }

    public static Map<Long, String> ListMapEntityToMap(List<MapEntity> listMap) {
        Map<Long, String> map = new HashMap<>();

        if (listMap != null && !listMap.isEmpty()) {
            for (MapEntity map1 : listMap) {
                map.put(map1.getKey(), (String) map1.getValue());
            }
        }
        return map;

    }

    public static Map<Integer, String> LongListMapToMap(List<Map<Integer, Object>> listMap) {
        Map<Integer, String> map = new HashMap<>();

        if (listMap != null && !listMap.isEmpty()) {
            for (Map<Integer, Object> map1 : listMap) {
                Integer key = null;
                String value = null;
                for (Map.Entry<Integer, Object> entry : map1.entrySet()) {
                    if ("key".equals(entry.getKey())) {
                        key = (Integer) entry.getValue();
                    } else if ("value".equals(entry.getKey())) {
                        value = (String) entry.getValue();
                    }
                }
                map.put(key, value);
            }
        }
        return map;

    }

    public static List<String> getMonthBetween(String minDate, String maxDate) {

            ArrayList<String> result = new ArrayList<String>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");//格式化为年月

            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();
        try {
            min.setTime(sdf.parse(minDate));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

            max.setTime(sdf.parse(maxDate));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
            Calendar curr = min;
            while (curr.before(max)) {
                result.add(sdf.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }

            return result;

    }

    public static String getMD5String(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            //一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 重新命名文件
     * 毫秒数+随机数
     * @return
     */
    public static String getFileNameNew() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return fmt.format(new Date())+"_"+UUID.randomUUID().toString();
    }


    //计算度差
    public static BigDecimal calcDiff(BigDecimal startNum, BigDecimal endNum) {

        BigDecimal diffNum = BigDecimal.ZERO;

        if (startNum == null) {
            startNum = BigDecimal.ZERO;
        }

        if (endNum == null) {
            endNum = BigDecimal.ZERO;
        }

        switch (endNum.compareTo(startNum)) {
            case -1:
                String full =
                        String.valueOf(startNum.setScale(0, RoundingMode.HALF_UP));//
                int length = full.length();// 获取长度
                diffNum = BigDecimal.TEN.pow(length).subtract(startNum).add(endNum);
                break;
            case 0:
                diffNum = BigDecimal.ZERO;
                break;
            case 1:
                diffNum = endNum.subtract(startNum);
                break;
            default:
                diffNum = endNum.subtract(startNum);
                break;
        }
        return  diffNum;

    }
}
