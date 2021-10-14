package net.sistransito.mobile.ait.table;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorTreeAdapter;
import android.widget.TextView;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.database.PrepopulatedDBOpenHelper;
import net.sistransito.R;

/**
 * Created by SANDRO on 04/12/2018.
 */

public class ListerExpandableAdapterTable extends CursorTreeAdapter {
    private LayoutInflater mInflator;
    private Context mycontext;

    public ListerExpandableAdapterTable(Cursor cursor, Context context) {
        super(cursor, context);
        mycontext = context;
        mInflator = LayoutInflater.from(context);
    }

    @Override
    protected void bindChildView(View arg0, Context arg1, Cursor arg2,
                                 boolean arg3) {
    }

    @Override
    protected void bindGroupView(View view, Context arg1, Cursor cursor,
                                 boolean arg3) {

        TextView tvTabelaArt, tvTabelaCodigo, tvTabelaDesdob, tvTabelaInfracao;

        tvTabelaArt = (TextView) view.findViewById(R.id.tv_tabela_art);
        tvTabelaCodigo = (TextView) view.findViewById(R.id.tv_tabela_codigo);
        tvTabelaDesdob = (TextView) view.findViewById(R.id.tv_tabela_desdob);
        tvTabelaInfracao = (TextView) view.findViewById(R.id.tv_tabela_descricao);

        tvTabelaCodigo
                .setText(cursor.getString(cursor
                        .getColumnIndex(PrepopulatedDBOpenHelper.AIT_FLAMING_CODE)));
        tvTabelaDesdob
                .setText("-" + cursor.getString(cursor
                        .getColumnIndex(PrepopulatedDBOpenHelper.AIT_UNFOLDING)));
        tvTabelaArt.setText("Art. " + cursor.getString(cursor
                .getColumnIndex(PrepopulatedDBOpenHelper.AIT_ARTICLE)));
        tvTabelaInfracao.setText(cursor.getString(cursor
                .getColumnIndex(PrepopulatedDBOpenHelper.AIT_DESCRIPTION)));

    }

    @Override
    protected View newChildView(Context arg0, Cursor arg1, boolean arg2,
                                ViewGroup arg3) {
        return null;
    }

    @Override
    protected View newGroupView(Context context, Cursor cursor,
                                boolean isExpendable, ViewGroup parent) {
        View view = mInflator.inflate(R.layout.article_table_listview_parent,
                null);

        TextView tvTabelaArt, tvTabelaCodigo, tvTabelaDesdob, tvTabelaInfracao;

        tvTabelaArt = (TextView) view.findViewById(R.id.tv_tabela_art);
        tvTabelaCodigo = (TextView) view.findViewById(R.id.tv_tabela_codigo);
        tvTabelaDesdob = (TextView) view.findViewById(R.id.tv_tabela_desdob);
        tvTabelaInfracao = (TextView) view.findViewById(R.id.tv_tabela_descricao);

        tvTabelaCodigo
                .setText(cursor.getString(cursor
                        .getColumnIndex(PrepopulatedDBOpenHelper.AIT_FLAMING_CODE)));
        tvTabelaDesdob
                .setText(cursor.getString(cursor
                        .getColumnIndex(PrepopulatedDBOpenHelper.AIT_UNFOLDING)));
        tvTabelaArt.setText(cursor.getString(cursor
                .getColumnIndex(PrepopulatedDBOpenHelper.AIT_ARTICLE)));
        tvTabelaInfracao.setText(cursor.getString(cursor
                .getColumnIndex(PrepopulatedDBOpenHelper.AIT_DESCRIPTION)));

        return view;
    }

    @Override
    protected Cursor getChildrenCursor(Cursor groupCursor) {

        int groupId = groupCursor.getInt(groupCursor
                .getColumnIndex(PrepopulatedDBOpenHelper.AIT_ID));

        return (DatabaseCreator.getPrepopulatedDBOpenHelper(mycontext))
                .getInfrationCursor(groupId);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View view, ViewGroup parent) {
        final int gPosition = groupPosition;

        final String gravidade = getGroup(gPosition).getString(
                getGroup(gPosition).getColumnIndex(
                        PrepopulatedDBOpenHelper.AIT_GRAVITY));

        final String responsavel = getGroup(gPosition).getString(
                getGroup(gPosition).getColumnIndex(
                        PrepopulatedDBOpenHelper.AIT_RESPONSIBLE));

        final String pontos = getGroup(gPosition).getString(
                getGroup(gPosition).getColumnIndex(
                        PrepopulatedDBOpenHelper.AIT_POINTS));

        final String valor = getGroup(gPosition).getString(
                getGroup(gPosition).getColumnIndex(
                        PrepopulatedDBOpenHelper.AIT_AMOUNT));

        final String competencia = getGroup(gPosition).getString(
                getGroup(gPosition).getColumnIndex(
                        PrepopulatedDBOpenHelper.AIT_TRANSIT_AUTHORITY));

        final String medidas = getGroup(gPosition).getString(
                getGroup(gPosition).getColumnIndex(
                        PrepopulatedDBOpenHelper.AIT_ADMINISTRATIVE_PROCEDURE));

        final String observacao = getGroup(gPosition).getString(
                getGroup(gPosition).getColumnIndex(
                        PrepopulatedDBOpenHelper.AIT_OBSERVATION));

        final String notas = getGroup(gPosition).getString(
                getGroup(gPosition).getColumnIndex(
                        PrepopulatedDBOpenHelper.AIT_ANNOTATIONS));

        View mView = null;
        if (view != null) {
            mView = view;
        } else {
            mView = mInflator.inflate(R.layout.article_table_listview_child,
                    parent, false);
        }

        TextView tvTabelaGravidade, tvTabelaPontuacao, tvTabelaValor, tvTabelaResponsavel,
                tvTabelaCompete, tvTabelaMedidas, tvTabelaObs, tvTabelaNotas;

        tvTabelaGravidade = (TextView) mView.findViewById(R.id.tv_tabela_gravidade);
        tvTabelaPontuacao = (TextView) mView.findViewById(R.id.tv_tabela_pontuacao);
        tvTabelaValor = (TextView) mView.findViewById(R.id.tv_tabela_valor);
        tvTabelaResponsavel = (TextView) mView.findViewById(R.id.tv_tabela_responsavel);
        tvTabelaCompete = (TextView) mView.findViewById(R.id.tv_tabela_competencia);
        tvTabelaMedidas = (TextView) mView.findViewById(R.id.tv_tabela_medidas);
        tvTabelaObs = (TextView) mView.findViewById(R.id.tv_tabela_obs);
        tvTabelaNotas = (TextView) mView.findViewById(R.id.tv_tabela_notas);

        tvTabelaGravidade.setText(gravidade);
        tvTabelaPontuacao.setText(pontos);
        tvTabelaValor.setText(mycontext.getResources().getString(R.string.cifrao_format) + valor);
        tvTabelaResponsavel.setText(responsavel);
        tvTabelaCompete.setText(competencia);
        tvTabelaMedidas.setText(medidas);
        tvTabelaObs.setText(observacao);
        tvTabelaNotas.setText(notas);

        return mView;
    }

}

