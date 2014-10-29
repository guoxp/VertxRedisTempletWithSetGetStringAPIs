package com.yizhao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.json.JsonObject;

import redis.clients.jedis.Jedis;

public class DownloadBinaryDatAPI extends PingVerticle {

	public DownloadBinaryDatAPI() {

	}

	public void download(final Vertx vertx, final HttpServerRequest bridge_between_server_and_client) {
		// Connecting to Redis on localhost
		Jedis jedis = new Jedis("localhost");
		byte[] key = { 'k' };

		JsonObject response = new JsonObject();
		response.putString("status", "0");
		response.putBinary("result", jedis.get(key));
		bridge_between_server_and_client.response().end(response.encodePrettily());
		
	}
}
