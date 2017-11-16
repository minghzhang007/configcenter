package com.lewis.configcenter.common.util;

import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by zhangminghua on 2016/11/10.
 */
public final class BeanUtils {

    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    private BeanUtils() {
    }

    public static Map<String, String> bean2Map(Object obj) {
        Map<String, String> retMap = Maps.newHashMap();
        if (obj != null) {
            try {
                PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(obj.getClass());
                if (ArrayUtils.isNotEmpty(propertyDescriptors)) {
                    for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                        if (!propertyDescriptor.getName().equals("class")) {
                            Method readMethod = propertyDescriptor.getReadMethod();
                            if (readMethod != null) {
                                Object value = readMethod.invoke(obj);
                                if (value instanceof String) {
                                    retMap.put(propertyDescriptor.getName(), (String) value);
                                } else {
                                    retMap.put(propertyDescriptor.getName(), JsonUtils.toString(value));
                                }
                            }
                        }
                    }
                }
            } catch (InvocationTargetException e) {
                logger.error("bean2Map occur InvocationTargetException {},param is {}", e.getCause(), JsonUtils.toString(obj));
            } catch (IllegalAccessException e) {
                logger.error("bean2Map occur IllegalAccessException {},param is {}", e.getCause(), JsonUtils.toString(obj));
            }
        }
        return retMap;
    }

    public static PropertyDescriptor[] getPropertyDescriptors(Class type) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            if (beanInfo != null) {
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                return propertyDescriptors;
            }
        } catch (IntrospectionException e) {
            logger.error("getPropertyDescriptors occur IntrospectionException {},param is {}", e.getCause(), type.getName());
        }
        return new PropertyDescriptor[0];
    }

    public static <T> T map2Bean(Map<String, String> map, Class<T> type) {
        T retValue = null;
        if (MapUtils.isNotEmpty(map) && type != null) {
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            try {
                retValue = type.newInstance();
                PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(type);
                while (it.hasNext()) {
                    final Map.Entry<String, String> entry = it.next();
                    PropertyDescriptor pd = Arrays.asList(propertyDescriptors).stream().filter(item -> Objects.equals(item.getName(), entry.getKey())).findFirst().orElse(null);
                    if (pd != null) {
                        Method writeMethod = pd.getWriteMethod();
                        if (writeMethod != null) {
                            Class<?> propertyType = pd.getPropertyType();
                            String value = entry.getValue();
                            if (StringUtil.isNotEmpty(value)) {
                                if (propertyType == String.class) {
                                    writeMethod.invoke(retValue, value);
                                } else {
                                    Object object = JsonUtils.toBean(value, propertyType);
                                    writeMethod.invoke(retValue, object);
                                }
                            }
                        }
                    }
                }
            } catch (InstantiationException e) {
                logger.error("map2Bean occur InstantiationException {},map is {},type is {}", e.getCause(), JsonUtils.toString(map), type.getName());
            } catch (IllegalAccessException e) {
                logger.error("map2Bean occur IllegalAccessException {},map is {},type is {}", e.getCause(), JsonUtils.toString(map), type.getName());
            } catch (InvocationTargetException e) {
                logger.error("map2Bean occur InvocationTargetException {},map is {},type is {}", e.getCause(), JsonUtils.toString(map), type.getName());
            }
        }
        return retValue;
    }


    public static Object getFieldValue(Object obj, String fieldName) {
        if (obj != null && StringUtil.isNotEmpty(fieldName)) {
            Field field = getField(obj.getClass(), fieldName);
            return getFieldValue(obj, field);
        }
        return null;
    }

    public static Field getField(Field[] fields, String fieldName) {
        if (ArrayUtils.isNotEmpty(fields) && StringUtil.isNotEmpty(fieldName)) {
            for (Field field : fields) {
                if (fieldName.equals(field.getName())) {
                    return field;
                }
            }
        }
        return null;
    }

    public static Object getFieldValue(Object obj, Field field) {
        if (obj != null && field != null) {
            boolean accessible = false;
            try {
                accessible = field.isAccessible();
                if (!accessible) {
                    field.setAccessible(true);
                }
                return field.get(obj);
            } catch (IllegalAccessException e) {
                logger.error("getFieldValue occur {},param is {},field is {}", e.getCause(), JsonUtils.toString(obj), field.getName());
            } finally {
                field.setAccessible(accessible);
            }
        }
        return null;
    }


    private static Field getField(Class type, String fieldName) {
        if (type != null && StringUtil.isNotEmpty(fieldName)) {
            Field[] fields = getFields(type, true);
            if (ArrayUtils.isNotEmpty(fields)) {
                for (Field field : fields) {
                    if (field.getName().equals(fieldName)) {
                        return field;
                    }
                }
            }
        }
        return null;
    }

    public static Field[] getFields(Class type, boolean includeParentClass) {
        List<Field> fieldList = new LinkedList<Field>();
        if (type != null) {
            Field[] declaredFields = type.getDeclaredFields();
            if (ArrayUtils.isNotEmpty(declaredFields)) {
                fieldList.addAll(Arrays.asList(declaredFields));
            }
            if (includeParentClass) {
                fieldList.addAll(Arrays.asList(getFields(type.getSuperclass(), includeParentClass)));
            }
        }
        return fieldList.toArray(new Field[fieldList.size()]);
    }

    public static Method[] getMethods(Class type, boolean includeParentClass) {
        List<Method> methodList = new LinkedList<Method>();
        if (type != null) {
            Method[] declaredMethods = type.getDeclaredMethods();
            if (ArrayUtils.isNotEmpty(declaredMethods)) {
                methodList.addAll(Arrays.asList(declaredMethods));
            }
            if (includeParentClass && type.getSuperclass() != Object.class) {
                methodList.addAll(Arrays.asList(getMethods(type.getSuperclass(), includeParentClass)));
            }
        }
        return methodList.toArray(new Method[methodList.size()]);
    }


}
