package musco.ubivelox.com;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.widget.ImageView;

public class MyHelloComputeActivity extends Activity {
    private Bitmap bitmapIn;
    private Bitmap bitmapOut;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        bitmapIn = loadBitmap(R.drawable.data);
        ImageView in = (ImageView) findViewById(R.id.displayin);
        in.setImageBitmap(bitmapIn);

        bitmapOut = Bitmap.createBitmap(bitmapIn.getWidth(),
                bitmapIn.getHeight(), bitmapIn.getConfig());
        ImageView out = (ImageView) findViewById(R.id.displayout);
        out.setImageBitmap(bitmapOut);

        processRenderscript();
    }

    private void processRenderscript() {
        RenderScript rs = RenderScript.create(this);
        Allocation inAllocation = Allocation.createFromBitmap(rs,
                bitmapIn, Allocation.MipmapControl.MIPMAP_NONE,
                Allocation.USAGE_SCRIPT);
        Allocation outAllocation = Allocation.createTyped(rs,
                inAllocation.getType());
        ScriptC_test script = new ScriptC_test(rs, getResources(),
                R.raw.test);

        script.set_in(inAllocation);
        script.set_out(outAllocation);
        script.set_script(script);
        script.invoke_filter();
        outAllocation.copyTo(bitmapOut);
    }

    private Bitmap loadBitmap(int resource) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeResource(getResources(), resource,
                options);
    }
}