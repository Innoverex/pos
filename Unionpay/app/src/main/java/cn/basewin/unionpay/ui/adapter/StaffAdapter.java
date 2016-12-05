package cn.basewin.unionpay.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.CommonAdapter;
import cn.basewin.unionpay.base.ViewHolder;
import cn.basewin.unionpay.entity.OperatorInfo;
import cn.basewin.unionpay.utils.DialogHelper;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.view.HintDialog;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/23 19:11<br>
 * 描述: 人员管理界面，员工的显示list<br>
 */
public class StaffAdapter extends CommonAdapter<OperatorInfo> {
    //
    public StaffAdapter(Context context, List<OperatorInfo> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    private TextView tv_item_staff;
    private RelativeLayout rl_item_staff;
    private Button btn_item_staff_delete;

    @Override
    public void convert(ViewHolder holder, final OperatorInfo operatorInfo) {
        tv_item_staff = holder.getView(R.id.tv_item_staff);
        rl_item_staff = holder.getView(R.id.rl_item_staff);
        btn_item_staff_delete = holder.getView(R.id.btn_item_staff_delete);
        tv_item_staff.setText(operatorInfo.getName());
        if (postion % 2 == 0) {
            rl_item_staff.setBackgroundResource(R.color.white);
        } else {
            rl_item_staff.setBackgroundResource(R.color.color_dedede);
        }
        btn_item_staff_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operatorInfo.getName().equals(AppConfig.operator_sys) || operatorInfo.getName().equals(AppConfig.operator_staff)) {
                    TLog.showToast("该操作员不能删除");
                    return;
                }
                HintDialog hdialog = DialogHelper.getHintDialog(mContext);
                hdialog.setTextHint("是否删除该柜员！");
                hdialog.setHintDialogListening(new HintDialog.HintDialogListening() {
                    @Override
                    public void ok() {
                        boolean b = OperatorInfo.dbDelete(operatorInfo);
                        if (b) {
                            try {
                                mDatas.remove(operatorInfo);
                            } catch (Exception e) {
                            }
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void calcel() {
                    }
                });
                hdialog.show();
            }
        });
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}
