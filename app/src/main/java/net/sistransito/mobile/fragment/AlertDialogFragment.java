package net.sistransito.mobile.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import net.sistransito.R;

public class AlertDialogFragment extends DialogFragment {

    private AnyDialogListener dialogListener;

    public static AlertDialogFragment newInstance(int title) {
        AlertDialogFragment frag = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");

        return new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setPositiveButton(R.string.ok_dialog,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                               //((FragmentAlertDialog)getActivity()).doPositiveClick();
                            }
                        }
                )

                .setNegativeButton(R.string.calcel_dialog,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //((FragmentAlertDialog)getActivity()).doNegativeClick();
                            }
                        }
                )
                .create();
    }
}
