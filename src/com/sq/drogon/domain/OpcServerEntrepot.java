package com.sq.drogon.domain;

import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.Group;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.Server;
import org.openscada.opc.lib.da.browser.Leaf;

import java.util.Collection;

/**
 * OPC服务信息仓库
 * 
 * @User: yaowenjie
 * @Date: 2016-06-15
 * @Time: 16:58
 */
public class OpcServerEntrepot {

	private boolean status;

	private int sysId;

	private Server server;

	private Group group;

	private Item[] itemArr;

	private ConnectionInformation connectionInformation;

	private Collection<Leaf> leafs;

	public ConnectionInformation getConnectionInformation() {
		return connectionInformation;
	}

	public void setConnectionInformation(
			ConnectionInformation connectionInformation) {
		this.connectionInformation = connectionInformation;
	}

	public Collection<Leaf> getLeafs() {
		return leafs;
	}

	public void setLeafs(Collection<Leaf> leafs) {
		this.leafs = leafs;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public int getSysId() {
		return sysId;
	}

	public void setSysId(int sysId) {
		this.sysId = sysId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Item[] getItemArr() {
		return itemArr;
	}

	public void setItemArr(Item[] itemArr) {
		this.itemArr = itemArr;
	}
	
	@Override
	public String toString() {
		return "OpcServerInfomation{" + ", status=" + status + ", sysId='"
				+ sysId + '\'' + ", server=" + server
				+ ", connectionInformation=" + connectionInformation.toString()
				+ ", leafs=" + ((leafs == null) ? 0 : leafs.size()) + '}';
	}
}
