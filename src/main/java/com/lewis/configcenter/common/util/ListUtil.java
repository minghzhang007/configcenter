package com.lewis.configcenter.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListUtil {
    private ListUtil() {
    }


    public static <T, K> Map<K, List<T>> groupBy(Collection<T> list, Function<? super T, K> classifier) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }
        return list.stream().filter(item -> item != null).collect(Collectors.groupingBy(classifier));
    }

    public static <T, K> Map<K, T> collectToMap(Collection<T> list, Function<? super T, K> keyMapper, Function<? super T, T> valueMapper) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }
        return list.stream().filter(item -> item != null).collect(Collectors.toMap(keyMapper, valueMapper, (oldKey, newKey) -> newKey));
    }

    public static <T, R> List<R> collectToList(Collection<T> list, Function<? super T, R> mapper) {
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        return list.stream().filter(item -> item != null).map(mapper).collect(Collectors.toList());
    }

    public static <T, R> Set<R> collectToSet(Collection<T> list, Function<? super T, R> mapper) {
        if (CollectionUtils.isEmpty(list)) {
            return Sets.newHashSet();
        }
        return list.stream().filter(item -> item != null).map(mapper).collect(Collectors.toSet());
    }


}
