package com.mooq.rxbusretrofitmvptest.network;

import com.mooq.mlibrary.network.model.BaseResponse;
import com.mooq.rxbusretrofitmvptest.Configs;
import com.mooq.rxbusretrofitmvptest.network.response.VideosResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by moq.
 * on 2019/4/30
 */
public interface APIService {

	@POST(Configs.ApiConfig.VIDEO_LINK)
	Observable<BaseResponse> getAllVedioBy(@Body boolean once_no);
}
