package org.support.project.knowledge.deploy.v0_6_0pre4;

import java.util.ArrayList;
import java.util.List;

import org.support.project.knowledge.deploy.Migrate;
import org.support.project.knowledge.entity.ItemChoicesEntity;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.ormapping.tool.dao.InitializeDao;
import org.support.project.web.bean.LoginedUser;

public class Migrate_0_6_0pre4 implements Migrate {

	public static Migrate_0_6_0pre4 get() {
		return org.support.project.di.Container.getComp(Migrate_0_6_0pre4.class);
	}

	@Override
	public boolean doMigrate() throws Exception {
		InitializeDao initializeDao = InitializeDao.get();
		String[] sqlpaths = { "/org/support/project/knowledge/deploy/v0_6_0pre4/migrate.sql" };
		initializeDao.initializeDatabase(sqlpaths);
		
		TemplateMastersEntity template = new TemplateMastersEntity();
		template.setTypeName("Weekly Report");
		template.setTypeIcon("fa-file-text-o");
		template.setDescription("プロジェクト毎の週報です");
		
		List<TemplateItemsEntity> items = new ArrayList<TemplateItemsEntity>();
		TemplateItemsEntity status = new TemplateItemsEntity();
		status.setItemName("Status");
		status.setItemType(TemplateLogic.ITEM_TYPE_RADIO);
		status.setDescription("状態");
		items.add(status);
		
		List<ItemChoicesEntity> choices = new ArrayList<>();
		ItemChoicesEntity status_good = new ItemChoicesEntity();
		status_good.setChoiceLabel("Good");
		status_good.setChoiceValue("1");
		choices.add(status_good);
		
		ItemChoicesEntity status_normal = new ItemChoicesEntity();
		status_normal.setChoiceLabel("Normal");
		status_normal.setChoiceValue("0");
		choices.add(status_normal);
		
		ItemChoicesEntity status_bad = new ItemChoicesEntity();
		status_bad.setChoiceLabel("Bad");
		status_bad.setChoiceValue("-1");
		choices.add(status_bad);
		
		status.setChoices(choices);
		
		TemplateItemsEntity problem = new TemplateItemsEntity();
		problem.setItemName("Problem");
		problem.setItemType(TemplateLogic.ITEM_TYPE_TEXT);
		problem.setDescription("問題点");
		items.add(problem);
		
		template.setItems(items);
		
		TemplateLogic.get().addTemplate(template, new LoginedUser());
		
		
		return true;

	}

}
