package com.yizhao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServerFileUpload;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.json.JsonObject;

import redis.clients.jedis.Jedis;

public class UploadBinaryDataAPI extends PingVerticle {
	public UploadBinaryDataAPI() {

	}

	public void upload(final Vertx vertx, final HttpServerRequest bridge_between_server_and_client) throws IOException {
		bridge_between_server_and_client.expectMultiPart(true);
		//bridge_between_server_and_client.expectMultiPart(true);
		bridge_between_server_and_client.uploadHandler(new Handler<HttpServerFileUpload>() {
			public void handle(final HttpServerFileUpload upload) {
				final Buffer buffer = new Buffer();
				upload.dataHandler(new Handler<Buffer>() {
					@Override
					public void handle(Buffer buffer) {
						buffer.appendBuffer(buffer);
					}
				}).endHandler(new Handler<Void>() {
					@Override
					public void handle(Void arg0) {
						JsonObject response = new JsonObject();
						response.putString("status", "0");
						response.putString("result", radis(buffer));
						bridge_between_server_and_client.response().end(response.encodePrettily());
					}

				});

			}
		});
	}

	public String radis(Buffer buffer) {
		// Connecting to Redis on localhost
		Jedis jedis = new Jedis("localhost");
		byte[] key = { 'k' };
		byte[] value = buffer.getBytes();
		return jedis.set(key, value);
	}
}
