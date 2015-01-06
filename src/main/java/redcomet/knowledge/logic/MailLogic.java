package redcomet.knowledge.logic;

import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.di.Container;
import redcomet.web.entity.ProvisionalRegistrationsEntity;

public class MailLogic {
	/** ログ */
	private static Log LOG = LogFactory.getLog(MailLogic.class);

	public static MailLogic get() {
		return Container.getComp(MailLogic.class);
	}

	public void sendInvitation(ProvisionalRegistrationsEntity entity) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
