package sit374_team17.propertyinspector;

// From tutorial

import retrofit2.Call;
import retrofit2.http.GET;


public interface RequestInterface {

    @GET("android/jsonandroid")
    Call<JSONResponse> getJSON();
}



