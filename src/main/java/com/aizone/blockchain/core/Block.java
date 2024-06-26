package com.aizone.blockchain.core;

import java.io.Serializable;

/**
 * 区块
 * @since 24-6-6
 */
public class Block implements Serializable {

	/**
	 * 区块 Header
	 */
	private BlockHeader header;
	/**
	 * 区块 Body
	 */
	private BlockBody body;

	public Block(BlockHeader header, BlockBody body) {
		this.header = header;
		this.body = body;
	}

	public Block() {
	}

	public BlockHeader getHeader() {
		return header;
	}

	public void setHeader(BlockHeader header) {
		this.header = header;
	}

	public BlockBody getBody() {
		return body;
	}

	public void setBody(BlockBody body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Block{" +
				"header=" + header +
				", body=" + body.toString() +
				'}';
	}
}
