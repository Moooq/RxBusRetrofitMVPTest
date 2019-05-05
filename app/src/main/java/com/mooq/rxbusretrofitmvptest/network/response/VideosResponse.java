package com.mooq.rxbusretrofitmvptest.network.response;

import com.mooq.mlibrary.network.model.BaseResponse;

import java.util.List;

/**
 * Created by moq.
 * on 2019/5/5
 */
public class VideosResponse extends BaseResponse {


	public class Subject {
		private int id;
		private String name;
		private String title;

		@Override
		public String toString() {
			return "name->"+name+"\n";

		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}

}
