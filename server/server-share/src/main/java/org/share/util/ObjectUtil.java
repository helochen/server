package org.share.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;


public class ObjectUtil {
	private static final Logger LOG = LoggerFactory.getLogger(ObjectUtil.class);

	private static short defaultShort = 0;

	/**
	 * 对象转换为short
	 * 
	 * @param val
	 * @return
	 */
	public static short obj2short(Object val) {
		return obj2short(val, defaultShort);
	}

	/**
	 * 对象转换为short
	 * 
	 * @param val
	 * @param defaultVal
	 *            默认值
	 * @return
	 */
	public static short obj2short(Object val, short defaultVal) {
		if (val == null) {
			return defaultVal;
		}

		if (val instanceof Number) {
			return ((Number) val).shortValue();
		} else if (val instanceof Boolean) {
			if ((Boolean) val) {
				return 1;
			}
			return 0;
		} else {
			if (strIsEmpty(val.toString()))
				return defaultVal;
			try {
				return Short.parseShort(val.toString());
			} catch (Exception e) {
				LOG.warn("[{}]转换short值失败", val);
				throw new RuntimeException("");
				// return defaultVal;
			}
		}
	}

	/**
	 * 对象转换为int
	 */
	public static int obj2int(Object val) {
		return obj2int(val, 0);
	}

	/**
	 * 对象转换为int
	 * 
	 * @param defaultVal
	 *            转换失败的默认值
	 */
	public static int obj2int(Object val, int defaultVal) {
		if (val == null) {
			return defaultVal;
		}

		if (val instanceof Number) {
			return ((Number) val).intValue();
		} else if (val instanceof Boolean) {
			if ((Boolean) val) {
				return 1;
			}
			return 0;
		} else {
			String temp = val.toString().trim();
			if (strIsEmpty(temp))
				return defaultVal;
			try {
				return Integer.parseInt(temp);
			} catch (Exception e) {
				LOG.warn("[{}]转换int值失败", val);
				throw new RuntimeException("转换int值失败--" + val);
				// return defaultVal;
			}
		}
	}

	/**
	 * Object转成String,如果为null就返回""
	 *
	 * @param value
	 * @return String
	 */
	public static String object2String(Object value) {
		if (value == null)
			return "";
		return value.toString();
	}

	/**
	 * Object转换成Str,如果为空就返回Null
	 * 
	 * @return
	 */
	public static String obj2StrOrNull(Object obj) {
		if (obj == null)
			return null;
		String str = obj.toString();
		if (str.trim().isEmpty())
			return null;
		return str;
	}

	/**
	 * 对象转换为boolean值<br>
	 * 如果是数字，大于0为true，否则为false
	 */
	public static boolean obj2bln(Object obj) {
		return obj2bln(obj, false);
	}

	/**
	 * 对象转换为boolean值<br>
	 * 如果是数字，大于0为true，否则为false
	 * 
	 * @param defaultVal
	 *            转换失败的默认值
	 */
	public static boolean obj2bln(Object obj, boolean defaultVal) {
		if (obj == null) {
			return defaultVal;
		}

		if (obj instanceof Boolean) {
			return ((Boolean) obj).booleanValue();
		} else if (obj instanceof Number) {
			return ((Number) obj).intValue() > 0;
		} else {
			return obj.toString().trim().equalsIgnoreCase("true");
		}
	}

	/**
	 * object转换为float,为空或转换失败，返回0
	 */
	public static float obj2float(Object obj) {
		return obj2float(obj, 0f);
	}

	/**
	 * object转换为float<br>
	 * 
	 * @param defaultVal
	 *            转换失败返回的默认值
	 */
	public static float obj2float(Object obj, float defaultVal) {
		if (obj == null) {
			return defaultVal;
		}

		if (obj instanceof Number) {
			return ((Number) obj).floatValue();

		} else {
			if (strIsEmpty(obj.toString())) {
				return defaultVal;
			}
			try {
				return Float.parseFloat(obj.toString());
			} catch (Exception e) {
				LOG.warn("[{}]转换float失败", obj);
				throw new RuntimeException("转换int值失败--" + obj);
				// return defaultVal;
			}
		}
	}

	/**
	 * object转换为float<br>
	 * 为空或转换失败，返回0
	 */
	public static double obj2double(Object obj) {
		return obj2double(obj, 0d);
	}

	/**
	 * object转换为float
	 * 
	 * @param defaultVal
	 *            为空或转换失败，返回的默认值
	 */
	public static double obj2double(Object obj, double defaultVal) {
		if (obj == null) {
			return defaultVal;
		}

		if (obj instanceof Number) {
			return ((Number) obj).doubleValue();
		} else {
			if (strIsEmpty(obj.toString())) {
				return defaultVal;
			}
			try {
				return Double.parseDouble(obj.toString().trim());
			} catch (Exception e) {
				LOG.warn("[{}]转换double失败", obj);
				return defaultVal;
			}
		}
	}

	/**
	 * object 转换为long<br>
	 * 若为空的时候返回0
	 */
	public static long obj2long(Object obj) {
		return obj2long(obj, 0l);
	}

	/**
	 * object 转换为long<br>
	 * 
	 * @param defaultVal
	 *            若为空的时候返回值
	 * @return
	 */
	public static long obj2long(Object obj, long defaultVal) {

		if (obj == null) {
			return 0l;
		}

		if (obj instanceof Number) {
			return ((Number) obj).longValue();
		} else {
			if (strIsEmpty(obj.toString())) {
				return defaultVal;
			}
			try {
				return Long.parseLong(obj.toString().trim());
			} catch (Exception e) {
				LOG.warn("[{}]转换Long失败", obj);
				return defaultVal;
			}
		}

	}

	/**
	 * obj 转换为String[]
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String[] obj2StringArray(Object obj) {
		if (obj == null) {
			return null;
		}

		if (obj instanceof String[]) {
			return (String[]) obj;

		} else if (obj instanceof Object[]) {
			Object[] objs = (Object[]) obj;
			String[] value = new String[objs.length];
			for (int i = 0; i < objs.length; i++) {
				value[i] = obj2StrOrNull(objs[i]);
			}
			return value;

		} else if (obj instanceof Collection) {
			Collection objs = (Collection) obj;
			String[] value = new String[objs.size()];
			int i = 0;
			for (Object o : objs) {
				value[i++] = obj2StrOrNull(o);
			}
			return value;
		}

		return null;
	}

	public static Timestamp obj2Timestamp(Object obj) {
		if (obj != null) {
			if (obj instanceof Timestamp) {
				return (Timestamp) obj;
			} else if (obj instanceof Date) {
				return new Timestamp(((Date) obj).getTime());
			} else if (obj instanceof Number) {
				return new Timestamp(((Number) obj).longValue());
			}
		}
		return null;
	}

	/**
	 * 是否为空
	 *
	 * @return true:为空null 或 ""
	 */
	public static boolean isEmpty(String val) {
		return null == val || val.trim().isEmpty();
	}

	/**
	 * 把某个值向上四舍五入
	 * 
	 * @param vo
	 *            转化值
	 * @return 取整浮点型值
	 */
	public static float exactValue(float vo) {
		BigDecimal bDec = new BigDecimal(vo);
		bDec = bDec.setScale(0, BigDecimal.ROUND_HALF_UP);
		return bDec.floatValue();
	}

	/**
	 * 判断字符串是否为空<br>
	 * null, "", "  "均返回true
	 * 
	 */
	public static boolean strIsEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}
}
