/**
 * 
 */
package org.javahispano.javaleague.server.dao.domain;

import org.javahispano.javaleague.shared.dto.BaseEntity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

/**
 * @author alfonso
 *
 */
@Index
@Entity
public class League extends BaseEntity {
	private String description;
	
}
