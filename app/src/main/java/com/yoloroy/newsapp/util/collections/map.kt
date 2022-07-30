package com.yoloroy.newsapp.util.collections

fun <K, V> Map<K, V>.swapKeysAndValues(): Map<V, K> {
    require(values.distinct().size == values.size)
    return map { (key, value) -> value to key }.toMap()
}
