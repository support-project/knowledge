package org.support.project.knowledge.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.ItemChoicesDao;
import org.support.project.knowledge.dao.TemplateItemsDao;
import org.support.project.knowledge.dao.TemplateMastersDao;
import org.support.project.knowledge.entity.ItemChoicesEntity;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.exception.InvalidParamException;

@DI(instance = Instance.Singleton)
public class TemplateLogic {
    private static final Log LOG = LogFactory.getLog(TemplateLogic.class);

    public static TemplateLogic get() {
        return Container.getComp(TemplateLogic.class);
    }
    
    public static final int TYPE_ID_KNOWLEDGE = -100;
    public static final int TYPE_ID_BOOKMARK = -99;
    public static final int TYPE_ID_EVENT = -101;
    public static final int TYPE_ID_PRESENTATION = -102;
    public static final int[] PROTECTED_TYPE_IDS = {TYPE_ID_KNOWLEDGE, TYPE_ID_BOOKMARK, TYPE_ID_EVENT, TYPE_ID_PRESENTATION};
    
    public static final int ITEM_TYPE_TEXT = 0;
    public static final int ITEM_TYPE_TEXTAREA = 1;
    public static final int ITEM_TYPE_INTEGER = 2;
    public static final int ITEM_TYPE_NUMBER = 3;
    public static final int ITEM_TYPE_RADIO = 10;
    public static final int ITEM_TYPE_CHECKBOX = 11;
    public static final int ITEM_TYPE_DATE = 20;
    public static final int ITEM_TYPE_TIME = 21;
    public static final int ITEM_TYPE_TIMEZONE = 22;
    public static final int[] ITEM_TYPE_NUMS = {
            ITEM_TYPE_TEXT, ITEM_TYPE_TEXTAREA, ITEM_TYPE_INTEGER, ITEM_TYPE_NUMBER, 
            ITEM_TYPE_RADIO, ITEM_TYPE_CHECKBOX,
            ITEM_TYPE_DATE, ITEM_TYPE_TIME, ITEM_TYPE_TIMEZONE
    };

    public static final String ITEM_TYPE_TEXT_STRING = "text";
    public static final String ITEM_TYPE_TEXTAREA_STRING = "textarea";
    public static final String ITEM_TYPE_INTEGER_STRING = "integer";
    public static final String ITEM_TYPE_NUMBER_STRING = "number";
    public static final String ITEM_TYPE_RADIO_STRING = "radio";
    public static final String ITEM_TYPE_CHECKBOX_STRING = "checkbox";
    public static final String ITEM_TYPE_RADIO_DATE = "date";
    public static final String ITEM_TYPE_RADIO_TIME = "time";
    public static final String ITEM_TYPE_RADIO_TIMEZONE = "timezone";
    public static final String[] ITEM_TYPE_STRINGS = {
            ITEM_TYPE_TEXT_STRING, ITEM_TYPE_TEXTAREA_STRING, ITEM_TYPE_INTEGER_STRING, ITEM_TYPE_NUMBER_STRING,
            ITEM_TYPE_RADIO_STRING, ITEM_TYPE_CHECKBOX_STRING,
            ITEM_TYPE_RADIO_DATE, ITEM_TYPE_RADIO_TIME, ITEM_TYPE_RADIO_TIMEZONE
    };
    
    
    public boolean isProtectedType(int typeId) {
        boolean result = false;
        for (int i : PROTECTED_TYPE_IDS) {
            if (i == typeId) {
                result = true;
            }
        }
        return result;
    }
    
    /**
     * 画面から取得した項目の種類のテキストを、DBに保存するInt値に変換
     * 
     * @param type
     * @return
     */
    public int convType(String type) {
        for (int i = 0; i < ITEM_TYPE_STRINGS.length; i++) {
            if (ITEM_TYPE_STRINGS[i].equals(type)) {
                return ITEM_TYPE_NUMS[i];
            }
        }
        LOG.warn("Item type: " + type + " is undefined. set default type: " + ITEM_TYPE_TEXT);
        return ITEM_TYPE_TEXT;
    }

    /**
     * DBの項目種類をテキストに変換
     * 
     * @param type
     * @return
     */
    public String convType(int type) {
        for (int i = 0; i < ITEM_TYPE_NUMS.length; i++) {
            if (ITEM_TYPE_NUMS[i] == type) {
                return ITEM_TYPE_STRINGS[i];
            }
        }
        LOG.warn("Item type: " + type + " is undefined. set default type text: " + ITEM_TYPE_TEXT_STRING);
        return ITEM_TYPE_TEXT_STRING;
    }

    /**
     * テンプレートを新規登録
     * 
     * @param template
     * @param loginedUser
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public TemplateMastersEntity addTemplate(TemplateMastersEntity template, LoginedUser loginedUser) {
        TemplateMastersDao templateDao = TemplateMastersDao.get();
        int id = templateDao.getNextId();
        template.setTypeId(id);
        // テンプレート保存
        TemplateMastersEntity insertedTemplate = templateDao.insert(template);
        Integer typeId = insertedTemplate.getTypeId();
        // テンプレートの入力項目を保存
        insertItems(insertedTemplate, typeId);
        return insertedTemplate;
    }

    /**
     * テンプレートを更新
     * 
     * @param template
     * @param loginedUser
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public TemplateMastersEntity updateTemplate(TemplateMastersEntity template, LoginedUser loginedUser) throws InvalidParamException {
        TemplateMastersDao templateDao = TemplateMastersDao.get();
        TemplateItemsDao itemsDao = TemplateItemsDao.get();
        ItemChoicesDao choicesDao = ItemChoicesDao.get();

        TemplateMastersEntity db = templateDao.selectOnKey(template.getTypeId());
        if (db == null) {
            MessageResult messageResult = new MessageResult();
            messageResult.setStatus(MessageStatus.Warning.getValue());
            messageResult.setCode(HttpStatus.SC_BAD_REQUEST);
            messageResult.setMessage("update data is not found");
            throw new InvalidParamException(messageResult);
        }

        // テンプレート更新
        db.setTypeName(template.getTypeName());
        db.setTypeIcon(template.getTypeIcon());
        db.setDescription(template.getDescription());
        db.setInitialValue(template.getInitialValue());
        templateDao.update(db);

        Integer typeId = template.getTypeId();
        if (isProtectedType(typeId)) {
            // 予約されたテンプレートの項目の増減はできない
            updateItems(template, typeId);
            return template;
        }

        // 項目、選択肢はデリートインサート
        List<TemplateItemsEntity> itemsEntities = itemsDao.physicalSelectOnTypeId(template.getTypeId());
        for (TemplateItemsEntity templateItemsEntity : itemsEntities) {
            if (LOG.isInfoEnabled()) {
                LOG.info("DELETE: " + templateItemsEntity.getTypeId() + ":" + templateItemsEntity.getItemNo());
            }
            itemsDao.physicalDelete(templateItemsEntity); // 物理削除
        }
        List<ItemChoicesEntity> choicesEntities = choicesDao.physicalSelectOnTypeId(template.getTypeId());
        for (ItemChoicesEntity itemChoicesEntity : choicesEntities) {
            choicesDao.physicalDelete(itemChoicesEntity); // 物理削除
        }

        // テンプレートの入力項目を保存
        insertItems(template, typeId);

        return db;
    }
    
    /**
     * 予約されたタイプの項目情報を更新
     * @param template
     * @param typeId
     */
    private void updateItems(TemplateMastersEntity template, Integer typeId) {
        TemplateItemsDao itemsDao = TemplateItemsDao.get();
        List<TemplateItemsEntity> itemsEntities = template.getItems();
        for (TemplateItemsEntity templateItemsEntity : itemsEntities) {
            templateItemsEntity.setTypeId(typeId);
            itemsDao.update(templateItemsEntity);
        }
    }

    /**
     * テンプレートの項目を登録 （先に物理削除しておき、全てインサートする）
     * 
     * @param template
     * @param itemsDao
     * @param typeId
     */
    private void insertItems(TemplateMastersEntity template, Integer typeId) {
        TemplateItemsDao itemsDao = TemplateItemsDao.get();
        ItemChoicesDao choicesDao = ItemChoicesDao.get();

        List<TemplateItemsEntity> itemsEntities = template.getItems();
        int itemNo = 1;
        for (TemplateItemsEntity templateItemsEntity : itemsEntities) {
            templateItemsEntity.setTypeId(typeId);
            templateItemsEntity.setItemNo(itemNo);
            itemsDao.insert(templateItemsEntity);

            int choiceNo = 0;
            List<ItemChoicesEntity> choicesEntities = templateItemsEntity.getChoices();
            for (ItemChoicesEntity itemChoicesEntity : choicesEntities) {
                itemChoicesEntity.setTypeId(typeId);
                itemChoicesEntity.setItemNo(itemNo);
                itemChoicesEntity.setChoiceNo(choiceNo);
                choicesDao.insert(itemChoicesEntity);
                choiceNo++;
            }
            itemNo++;
        }
    }

    /**
     * テンプレートの削除
     * 
     * @param typeId
     * @param loginedUser
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void deleteTemplate(Integer typeId, LoginedUser loginedUser) {
        TemplateMastersDao templateDao = TemplateMastersDao.get();
        TemplateItemsDao itemsDao = TemplateItemsDao.get();
        ItemChoicesDao choicesDao = ItemChoicesDao.get();

        TemplateMastersEntity entity = templateDao.selectOnKey(typeId);
        if (entity != null) {
            templateDao.delete(entity);
        }

        List<TemplateItemsEntity> itemsEntities = itemsDao.selectOnTypeId(typeId);
        for (TemplateItemsEntity templateItemsEntity : itemsEntities) {
            itemsDao.delete(templateItemsEntity);
        }

        List<ItemChoicesEntity> choicesEntities = choicesDao.selectOnTypeId(typeId);
        for (ItemChoicesEntity itemChoicesEntity : choicesEntities) {
            choicesDao.delete(itemChoicesEntity);
        }
    }

    /**
     * テンプレートの情報を取得
     * 
     * @param id
     * @return
     */
    public TemplateMastersEntity loadTemplate(Integer id) {
        TemplateMastersDao mastersDao = TemplateMastersDao.get();
        TemplateMastersEntity entity = mastersDao.selectOnKey(id);

        TemplateItemsDao itemsDao = TemplateItemsDao.get();
        List<TemplateItemsEntity> itemsEntities = itemsDao.selectOnTypeId(id);
        entity.setItems(itemsEntities);
        Map<Integer, TemplateItemsEntity> itemMap = new HashMap<Integer, TemplateItemsEntity>();
        for (TemplateItemsEntity templateItemsEntity : itemsEntities) {
            templateItemsEntity.setChoices(new ArrayList<ItemChoicesEntity>());
            itemMap.put(templateItemsEntity.getItemNo(), templateItemsEntity);
        }

        ItemChoicesDao choicesDao = ItemChoicesDao.get();
        List<ItemChoicesEntity> choicesEntities = choicesDao.selectOnTypeId(id);
        // 念のためソート
        Collections.sort(choicesEntities, new Comparator<ItemChoicesEntity>() {
            @Override
            public int compare(ItemChoicesEntity o1, ItemChoicesEntity o2) {
                if (!o1.getTypeId().equals(o2.getTypeId())) {
                    return o1.getTypeId().compareTo(o2.getTypeId());
                }
                if (!o1.getItemNo().equals(o2.getItemNo())) {
                    return o1.getItemNo().compareTo(o2.getItemNo());
                }
                if (!o1.getChoiceNo().equals(o2.getChoiceNo())) {
                    return o1.getChoiceNo().compareTo(o2.getChoiceNo());
                }
                return 0;
            }
        });
        for (ItemChoicesEntity itemChoicesEntity : choicesEntities) {
            if (itemMap.containsKey(itemChoicesEntity.getItemNo())) {
                TemplateItemsEntity templateItemsEntity = itemMap.get(itemChoicesEntity.getItemNo());
                templateItemsEntity.getChoices().add(itemChoicesEntity);
            }
        }
        
        boolean editable = true;
        if (TemplateLogic.get().isProtectedType(id)) {
            editable = false;
        }
        entity.setEditable(editable);
        
        return entity;
    }

    public List<TemplateMastersEntity> selectAll() {
        List<TemplateMastersEntity> templates = TemplateMastersDao.get().selectAll();
        // ソート
        Collections.sort(templates, new Comparator<TemplateMastersEntity>() {
            @Override
            public int compare(TemplateMastersEntity o1, TemplateMastersEntity o2) {
                if (o1.getTypeId().intValue() == TemplateLogic.TYPE_ID_KNOWLEDGE) {
                    return -1;
                }
                if (o2.getTypeId().intValue() == TemplateLogic.TYPE_ID_KNOWLEDGE) {
                    return 1;
                }
                if (o1.getTypeId().intValue() == TemplateLogic.TYPE_ID_BOOKMARK) {
                    return -1;
                }
                if (o2.getTypeId().intValue() == TemplateLogic.TYPE_ID_BOOKMARK) {
                    return 1;
                }
                if (o1.getTypeId().intValue() == TemplateLogic.TYPE_ID_EVENT) {
                    return -1;
                }
                if (o2.getTypeId().intValue() == TemplateLogic.TYPE_ID_EVENT) {
                    return 1;
                }
                if (o1.getTypeId().intValue() == TemplateLogic.TYPE_ID_PRESENTATION) {
                    return -1;
                }
                if (o2.getTypeId().intValue() == TemplateLogic.TYPE_ID_PRESENTATION) {
                    return 1;
                }
                return o1.getTypeId().compareTo(o2.getTypeId());
            }
        });
        return templates;
    }

    public TemplateMastersEntity selectOnName(String template) {
        TemplateMastersEntity entity = TemplateMastersDao.get().selectOnName(template);
        if (entity == null) {
            return null;
        }
        return loadTemplate(entity.getTypeId());
    }

}
