package com.lewis.configcenter.biz.util;

import com.google.common.collect.Lists;
import com.lewis.configcenter.biz.model.entity.ItemDO;
import com.lewis.configcenter.common.util.JsonUtils;
import com.lewis.configcenter.common.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

/**
 * @author zhangminghua
 */
@Data
@NoArgsConstructor
public class ConfigChangeSetBuilder {

    private List<ItemDO> createItems = Lists.newLinkedList();

    private List<ItemPair> updateItems = Lists.newLinkedList();

    private List<ItemDO> deleteItems = Lists.newLinkedList();

    public ConfigChangeSetBuilder createItem(ItemDO itemDO) {
        if (StringUtil.isNotEmpty(itemDO.getDictKey())) {
            createItems.add(itemDO);
        }
        return this;
    }

    public ConfigChangeSetBuilder updateTime(ItemDO newItem, ItemDO oldItem) {
        if (!Objects.equals(newItem.getValue(), oldItem.getValue())) {
            ItemPair pair = new ItemPair(oldItem, newItem);
            updateItems.add(pair);
        }
        return this;
    }

    public ConfigChangeSetBuilder deleteItem(ItemDO itemDO) {
        if (StringUtil.isNotEmpty(itemDO.getDictKey())) {
            deleteItems.add(itemDO);
        }
        return this;
    }

    public boolean hasContent() {
        return !createItems.isEmpty() || !updateItems.isEmpty() || !deleteItems.isEmpty();
    }

    public String build() {
        for (ItemDO createItem : createItems) {
            createItem.setUpdateTime(System.currentTimeMillis());
        }
        for (ItemPair updateItem : updateItems) {
            updateItem.newItem.setUpdateTime(System.currentTimeMillis());
            updateItem.oldItem.setUpdateTime(System.currentTimeMillis());
        }
        for (ItemDO deleteItem : deleteItems) {
            deleteItem.setUpdateTime(System.currentTimeMillis());
        }
        return JsonUtils.toString(this);
    }

    static class ItemPair {

        ItemDO oldItem;
        ItemDO newItem;

        public ItemPair(ItemDO oldItem, ItemDO newItem) {
            this.oldItem = oldItem;
            this.newItem = newItem;
        }
    }
}
