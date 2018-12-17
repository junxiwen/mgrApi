package com.ys.mgr.util;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * Date: 2017/12/22 13:06
 */
public class MyCollectionUtils {

    public static boolean isEmpty(final Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isNotEmpty(final Collection<?> coll) {
        return !isEmpty(coll);
    }

    public static String[] toStringArray(Collection<String> list) {
        return list.toArray(new String[]{});
    }

    public static Integer[] toIntArray(Collection<Integer> list) {
        return list.toArray(new Integer[]{});
    }

    public static Long[] toLongArray(Collection<Long> list) {
        return list.toArray(new Long[]{});
    }

//    public static <E> ArrayList<E> newArrayList(E... elements) {
//        return Lists.newArrayList(elements);
//    }

    /*public static <E> List<E> sum(final List<? extends E> list1, final List<? extends E> list2) {
        return ListUtils.sum(list1, list2);
    }

    public static <E> List<E> union(final List<? extends E> list1, final List<? extends E> list2) {
        return ListUtils.union(list1, list2);
    }*/
}
