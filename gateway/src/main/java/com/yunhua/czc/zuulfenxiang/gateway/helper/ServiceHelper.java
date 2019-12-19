package com.yunhua.czc.zuulfenxiang.gateway.helper;

import com.yunhuakeji.component.base.annotation.entity.Code;
import com.yunhuakeji.component.base.bean.entity.EntityIFace;
import com.yunhuakeji.component.base.enums.entity.StateEnum;
import com.yunhuakeji.component.base.exception.BusinessException;
import com.yunhuakeji.component.mybatis.service.base.BaseService;
import com.yunhuakeji.component.mybatis.service.base.impl.BaseServiceImpl;
import com.yunhuakeji.component.mybatis.util.MapperUtil;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author chenzhicong
 * @time 2019/12/17 11:50
 * @description 对baseService进行符合个人习惯的增强补充
 */
public class ServiceHelper<T extends EntityIFace> {
    private BaseService<T> baseService;

    public ServiceHelper(BaseService baseService) {
        this.baseService = baseService;
    }

    public static <T extends EntityIFace> ServiceHelper<T> ofService(BaseService<T> baseService) {
        return new ServiceHelper<T>(baseService);
    }

    /**
     * 根据传入一个字段的字段名称和值查询条件，修改另一个字段的值
     */
    public void updateOneColumnByOneColumn(String column, Object columnSetVal, @NotBlank String whereColumn, @NotBlank String entityCodeValue, Integer universityId) {
        Example example = getOneColumnExample(whereColumn, entityCodeValue, universityId);
        Class<T> tClass = getEntityClass(baseService.getClass());
        try {
            T t = tClass.newInstance();
            Field f = tClass.getDeclaredField(column);
            if (!f.isAccessible()) {
                f.setAccessible(true);
            }
            f.set(t, columnSetVal);
            baseService.updateByExampleSelective(t, example);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    /**
     * 根据一个字段删除
     */
    public int deleteByOneColumn(@NotBlank String column, @NotBlank String entityCodeValue, Integer universityId) {
        Example example = getOneColumnExample(column, entityCodeValue, universityId);
        Class<T> tClass = getEntityClass(baseService.getClass());
        try {
            T t = tClass.newInstance();
            Field f = tClass.getSuperclass().getSuperclass().getDeclaredField("state");
            f.setAccessible(true);
            f.set(t, StateEnum.INVALID);
            return baseService.updateByExampleSelective(t, example);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    /**
     * 根据一个字段IN删除
     */
    public long deleteByOneColumnIN(@NotBlank String column, @NotBlank Collection values, Integer universityId) {
        if (CollectionUtils.isEmpty(values)) {
            return 0;
        }
        T t = null;
        try {
            Class<T> tClass = getEntityClass(baseService.getClass());
            t = tClass.newInstance();
            Field f = tClass.getSuperclass().getSuperclass().getDeclaredField("state");
            f.setAccessible(true);
            f.set(t, StateEnum.INVALID);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
        /**
         * 控制in查询的元素数量不超过500
         */
        if (values.size() > 500) {
            List<List> lists = ListUtil.split(new LinkedList(values), 500);
            long retCount = 0L;
            for (List list : lists) {
                Example example = getOneColumnInExample(column, list, universityId);
                retCount = retCount + baseService.updateByExampleSelective(t, example);
            }
            return retCount;
        } else {
            Example example = getOneColumnInExample(column, values, universityId);
            return baseService.updateByExampleSelective(t, example);
        }

    }

    /**
     * 查询全部
     */
    public List<T> selectAll(Integer universityId) {
        LinkedHashMap<String, Object> paramMap = new LinkedHashMap();
        if (null != universityId) {
            paramMap.put("universityId", universityId);
        }
        paramMap.put("state", StateEnum.VALID.getDbCode());
        Example example = MapperUtil.packageGeneralExample(paramMap, null, getEntityClass(baseService.getClass()));
        List<T> tList = baseService.selectByExample(example);
        return tList;
    }

    /**
     * 根据一个字段查询唯一记录
     */
    public T selectOneByOneColumn(@NotBlank String column, @NotBlank String entityCodeValue, Integer universityId) {
        Example example = getOneColumnExample(column, entityCodeValue, universityId);
        List<T> tList = baseService.selectByExample(example);
        if (CollectionUtils.isEmpty(tList)) {
            return null;
        }
        if (tList.size() > 1) {
            throw new BusinessException("DATA_ERROR", "数据错误");
        }
        return tList.get(0);
    }

    /**
     * 根据一个字段In查询，返回Map,键为查询条件的值，值为满足条件的记录实体（多个记录满足只取一个）。
     */
    public Map<String, T> selectByOneColumnIn4One2OneMap(@NotEmpty String column, Collection entityCodeValues, Integer universityId) {
        if (CollectionUtils.isEmpty(entityCodeValues)) {
            return new HashMap<>();
        }
        List<T> tList = selectByOneColumnIn(column, entityCodeValues, universityId);
        Class<T> tClass = getEntityClass(baseService.getClass());
        Map<String, T> map = new HashMap<>();
        try {
            Field f = tClass.getDeclaredField(column);
            if (!f.isAccessible()) {
                f.setAccessible(true);
            }
            for (T t : tList) {
                String k = (String) f.get(t);
                map.put(k, t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
        return map;
    }

    /**
     * 根据一个字段In查询，返回map，键为查询条件字段的值，值为满足条件的记录集合
     */
    public Map<String, List<T>> selectByOneColumnIn4Map(@NotBlank String column, Collection values, Integer universityId) {
        if (CollectionUtils.isEmpty(values)) {
            return new HashMap<>();
        }
        List<T> tList = selectByOneColumnIn(column, values, universityId);
        Class<T> tClass = getEntityClass(baseService.getClass());
        Map<String, List<T>> map = new HashMap<>();
        try {
            Field f = tClass.getDeclaredField(column);
            if (!f.isAccessible()) {
                f.setAccessible(true);
            }
            for (T t : tList) {
                String k = String.valueOf(f.get(t));
                if (!map.containsKey(k)) {
                    List<T> ts = new ArrayList<>();
                    ts.add(t);
                    map.put(k, ts);
                } else {
                    map.get(k).add(t);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
        return map;
    }

    /**
     * 根据一个字段查询，返回List
     */
    public List<T> selectByOneColumn(@NotBlank String column, @NotBlank String entityCodeValue, Integer universityId) {
        Example example = getOneColumnExample(column, entityCodeValue, universityId);
        List<T> tList = baseService.selectByExample(example);
        return tList;
    }

    /**
     * 根据一个字段查询，返回Count
     */
    public Integer selectCountByOneColumn(@NotBlank String column, @NotBlank String entityCodeValue, Integer universityId) {
        Example example = getOneColumnExample(column, entityCodeValue, universityId);
        Integer count = baseService.selectCountByExample(example);
        return count;
    }

    /**
     * 根据一个字段IN查询，返回List
     */
    public List<T> selectByOneColumnIn(@NotBlank String column, @NotBlank Collection values, Integer universityId) {
        if (values.isEmpty()) {
            return new ArrayList<>();
        }

        /**
         * 控制in查询的元素数量不超过500
         */
        List<T> tList = null;
        if (values.size() > 500) {
            List<List> lists = ListUtil.split(new LinkedList(values), 500);
            tList = new LinkedList<>();
            for (List list : lists) {
                Example example = getOneColumnInExample(column, list, universityId);
                List<T> thisList = baseService.selectByExample(example);
                tList.addAll(thisList);
            }
        } else {
            Example example = getOneColumnInExample(column, values, universityId);
            tList = baseService.selectByExample(example);
        }

        return tList;
    }


    /**
     * 反射获取code字段，根据code值查询
     */
    public T selectByEntityCode(String entityCodeValue, Integer universityId) {
        Class<T> tClass = getEntityClass(baseService.getClass());
        Field[] fields = tClass.getDeclaredFields();
        String fieldS = null;
        for (Field field : fields) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            if (field.getAnnotation(Code.class) != null) {
                fieldS = field.getName();
            }
        }
        return baseService.selectByEntityCode(fieldS, entityCodeValue, universityId);
    }

    /**
     * 根据一个字段右模糊查询，返回List
     */
    public List<T> selectByOneColumnRlike(@NotBlank String column, @NotBlank String value, Integer universityId){
        Example example = getOneColumnExample(column+"_RLIKE", value, universityId);
        List<T> tList = baseService.selectByExample(example);
        return tList;
    }



    private Example getOneColumnExample(@NotBlank String column, @NotBlank String entityCodeValue, Integer universityId) {
        LinkedHashMap<String, Object> paramMap = new LinkedHashMap(4);
        paramMap.put(column, entityCodeValue);
        if (null != universityId) {
            paramMap.put("universityId", universityId);
        }
        paramMap.put("state", StateEnum.VALID.getDbCode());
        Example example = MapperUtil.packageGeneralExample(paramMap, null, getEntityClass(baseService.getClass()));
        return example;
    }


    private Example getOneColumnInExample(@NotBlank String column, @NotBlank Collection<Object> entityCodeValue, Integer universityId) {
        LinkedHashMap<String, Object> paramMap = new LinkedHashMap(4);
        paramMap.put(column + "_IN", entityCodeValue);
        if (null != universityId) {
            paramMap.put("universityId", universityId);
        }
        paramMap.put("state", StateEnum.VALID.getDbCode());
        Example example = MapperUtil.packageGeneralExample(paramMap, null, getEntityClass(baseService.getClass()));
        return example;
    }

    private void insertOrUpdate() {

    }


    /**
     * 获取实体类型，注意直接获取父类的实体泛型获取不到，
     * 因为这里可能注入的实际上是Cglib代理类，需要递归的去取父类
     */
    private Class getEntityClass(Class c) {
        if (c.getSuperclass() == Object.class) {
            return Object.class;
        } else {
            if (c.getSuperclass() == BaseServiceImpl.class) {
                Type[] types = ((ParameterizedType) c.getGenericSuperclass()).getActualTypeArguments();
                return (Class) types[0];
            } else {
                return getEntityClass(c.getSuperclass());
            }
        }
    }

}
