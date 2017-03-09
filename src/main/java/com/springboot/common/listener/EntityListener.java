package com.springboot.common.listener;


import com.springboot.common.entity.BaseEntity;

import javax.persistence.PrePersist;
import java.util.Date;

public class EntityListener {

	@PrePersist void onPrePersist(BaseEntity o) {
		o.setCreationTime(new Date());
		o.setLastUpdateTime(new Date());
	}
}
