package net.shopxx.merge.entity;

import net.shopxx.entity.BaseEntity;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;

/**
 * 熊猫中医与shop用户关系
 */
@Entity
@Table(name = "users_relation")
public class UsersRelation extends BaseEntity<Long> {

		private Long userId;

		/**
		 * 页面关键词
		 */
		@Length(max = 100)
		private String ipandatcmUserId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getIpandatcmUserId() {
		return ipandatcmUserId;
	}

	public void setIpandatcmUserId(String ipandatcmUserId) {
		this.ipandatcmUserId = ipandatcmUserId;
	}
}