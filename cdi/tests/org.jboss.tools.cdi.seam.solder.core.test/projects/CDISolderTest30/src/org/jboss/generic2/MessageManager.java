package org.jboss.generic2;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jboss.seam.solder.bean.generic.ApplyScope;
import org.jboss.seam.solder.bean.generic.Generic;
import org.jboss.seam.solder.bean.generic.GenericConfiguration;

@GenericConfiguration(ACMEQueue.class)
class MessageManager {

	@Inject
	@Generic
	MessageQueue queue;

	@Produces
	@ApplyScope
	MessageDispatcher messageDispatcherProducer() {
		return queue.createMessageDispatcher();
	}

	@Produces
	DispatcherPolicy getPolicy() {
		return queue.getDispatcherPolicy();
	}
}
