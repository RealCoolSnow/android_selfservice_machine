package app.selfservice.machine.activity;

import android.widget.Toast;

import app.selfservice.machine.api.bean.req.HelloReq;
import app.selfservice.machine.api.bean.resp.HelloResp;
import app.selfservice.machine.base.BaseActivity;
import app.selfservice.machine.databinding.ActivityMainBinding;
import app.selfservice.machine.network.RetrofitFactory;
import app.tv.network.BaseObserver;
import app.tv.network.RxScheduler;
import app.tv.network.bean.BaseResp;
import io.reactivex.Observable;


public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void initView() {
        binding.btnHttpTest.setOnClickListener(view -> httpTest());
    }

    private void httpTest() {
        HelloReq helloReq = new HelloReq();
        Observable<BaseResp<HelloResp>> observable = RetrofitFactory.getInstance().hello(helloReq.toRequestBody());
        observable.compose(RxScheduler.compose(this.bindToLifecycle())).subscribe(new BaseObserver<HelloResp>(this) {
            @Override
            protected void onSuccess(String msg, HelloResp data) {
                Toast.makeText(MainActivity.this, data.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onError(int code, String msg) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
