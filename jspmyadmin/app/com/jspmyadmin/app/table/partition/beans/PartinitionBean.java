/**
 * 
 */
package com.jspmyadmin.app.table.partition.beans;

import java.util.ArrayList;
import java.util.List;

import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/08
 *
 */
public class PartinitionBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String partition = null;
	private String partition_val = null;
	private String partitions = null;

	private String[] names = null;

	private List<PartitionInfo> partition_list = null;

	private List<String> type_list = new ArrayList<String>(FrameworkConstants.Utils.PARTITION_LIST);

	/**
	 * @return the partition
	 */
	public String getPartition() {
		return partition;
	}

	/**
	 * @param partition
	 *            the partition to set
	 */
	public void setPartition(String partition) {
		this.partition = partition;
	}

	/**
	 * @return the partition_val
	 */
	public String getPartition_val() {
		return partition_val;
	}

	/**
	 * @param partition_val
	 *            the partition_val to set
	 */
	public void setPartition_val(String partition_val) {
		this.partition_val = partition_val;
	}

	/**
	 * @return the partitions
	 */
	public String getPartitions() {
		return partitions;
	}

	/**
	 * @param partitions
	 *            the partitions to set
	 */
	public void setPartitions(String partitions) {
		this.partitions = partitions;
	}

	/**
	 * @return the names
	 */
	public String[] getNames() {
		return names;
	}

	/**
	 * @param names
	 *            the names to set
	 */
	public void setNames(String[] names) {
		this.names = names;
	}

	/**
	 * @return the partition_list
	 */
	public List<PartitionInfo> getPartition_list() {
		return partition_list;
	}

	/**
	 * @param partition_list
	 *            the partition_list to set
	 */
	public void setPartition_list(List<PartitionInfo> partition_list) {
		this.partition_list = partition_list;
	}

	/**
	 * @return the type_list
	 */
	public List<String> getType_list() {
		return type_list;
	}

	/**
	 * @param type_list
	 *            the type_list to set
	 */
	public void setType_list(List<String> type_list) {
		this.type_list = type_list;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// extra methods
	/**
	 * 
	 * @return
	 */
	public String getP_count() {
		if (partition_list != null) {
			return String.valueOf(partition_list.size());
		}
		return FrameworkConstants.ZERO;
	}
}
