package com.example.newsapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontStyle;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.azeesoft.lib.colorpicker.ColorPickerDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TextEditorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TextEditorFragment extends Fragment implements AdapterView.OnItemSelectedListener,OnListClickListner  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int imageList[]={R.drawable.ic_text_undo,R.drawable.ic_text_font,R.drawable.ic_text_size,R.drawable.ic_align_left,R.drawable.ic_align_centre,R.drawable.ic_align_right,R.drawable.ic_bold,R.drawable.ic_italics,R.drawable.ic_underline,R.drawable.ic_color,R.drawable.ic_color_text};
    EditText editor;
    String size[]={"2","4","6","8","10","12","14","16","18","20","22","24","26","28","30","32","34","36","38","40","42","44","46","48","50","52","54","56","58","60","62","64","66","68","70"};
    String font[]={"BeautifulPeoplePersonalUse-dE0g","BunchBlossomsPersonalUse-0nA4","CinderelaPersonalUseRegular-RDvM","Countryside-YdKj","Halimun-W7jn","LemonJellyPersonalUse-dEqR","MetalsmithRegular-x3yMq","QuickKissPersonalUse-PxlZ","Tomatoes-O8L8","VeganStylePersonalUse-5Y58"};
    ColorPickerDialog colorPickerDialog;
    int currentColor;
    int currentSize;
    String currentFont;
    NotesData notesData;
    public TextEditorFragment() {
        // Required empty public constructor
    }
    public TextEditorFragment(NotesData notesData)
    {
        this.notesData=notesData;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TextEditorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TextEditorFragment newInstance(String param1, String param2) {
        TextEditorFragment fragment = new TextEditorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        colorPickerDialog=ColorPickerDialog.createColorPickerDialog(this.getContext(),ColorPickerDialog.DARK_THEME);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_text_editor, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView=view.findViewById(R.id.editActionList);
        Toolbar toolbar=view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.tool_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuId=item.getItemId();
                if(menuId==R.id.clear)
                {
                    editor.setText("");
                }
                else if(menuId==R.id.delete)
                {
                    DataBaseHandler db=new DataBaseHandler(getContext());
                    db.deleteNotes(notesData.getTitle());
                }
                else if(menuId==R.id.save)
                {
                    DataBaseHandler db=new DataBaseHandler(getContext());
                    SpansToString spansToString=new SpansToString();
                    Spannable t=editor.getText();
                    String json=spansToString.toJsonString(t);
                    db.upgradeNotes(notesData.getTitle(),Html.toHtml(editor.getText()),json);
                }
                return true;
            }
        });
        Spinner sizeSpinner=view.findViewById(R.id.sizeSpin);
        sizeSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> sizeadapter=new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,size);
        sizeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeadapter);
        Spinner fontSpinner=view.findViewById(R.id.fontSpin);
        fontSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> fontadapter=new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,font);
        fontadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSpinner.setAdapter(fontadapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        EditListAdapter listAdapter=new EditListAdapter(getContext(),imageList,this);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(layoutManager);
        editor=view.findViewById(R.id.editor);
        SpansToString spans=new SpansToString();
        editor.setText(spans.applySpan(new SpannableString(Html.fromHtml(notesData.getNote())),notesData.getJson(),getActivity()));
        Button colorButton=view.findViewById(R.id.colorButton);
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              colorPickerDialog.show();
              colorPickerDialog.setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
                  @Override
                  public void onColorPicked(int color, String hexVal) {
                      currentColor=color;
                      colorButton.setBackgroundColor(color);
                      colorPickerDialog.hide();
                  }
              });
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Spannable text=editor.getText();
        int i=editor.getSelectionStart();
        int f=editor.getSelectionEnd();
        switch(position)
        {
            case 0:
            {
                //BackgroundColorSpan backColor=new BackgroundColorSpan(Color.WHITE);
                ForegroundColorSpan foreColor=new ForegroundColorSpan(Color.BLACK);
                //text.setSpan(backColor,i,f,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                text.setSpan(foreColor,i,f,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                StyleSpan[] styleSpan=text.getSpans(i,f,StyleSpan.class);
                BackgroundColorSpan[] back=text.getSpans(i,f,BackgroundColorSpan.class);
                UnderlineSpan[] underLine=text.getSpans(i,f,UnderlineSpan.class);
                for(int x=0;x<underLine.length;x++)
                {
                    text.removeSpan(underLine[x]);
                }
                for(int x=0;x<back.length;x++)
                {
                    text.removeSpan(back[x]);
                }
                for(int x=0;x<styleSpan.length;x++)
                {
                    text.removeSpan(styleSpan[x]);
                }

                break;
            }
            case 1:
            {
                Typeface font=Typeface.createFromAsset(getActivity().getAssets(),currentFont+".ttf");
                text.setSpan(new CustomTypefaceSpan(font,currentFont+".ttf"),i,f,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            }
            case 2:
            {
                AbsoluteSizeSpan sizeSpan=new AbsoluteSizeSpan(currentSize,true);
                text.setSpan(sizeSpan,i,f,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            }
            case 3:
            {
                AlignmentSpan align=new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL);
                text.setSpan(align,i,f,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            }
            case 4:
            {
                AlignmentSpan align=new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER);
                text.setSpan(align,i,f,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            }
            case 5:
            {
                AlignmentSpan align=new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE);
                text.setSpan(align,i,f,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            }
            case 6:
            {
                StyleSpan style=new StyleSpan(Typeface.BOLD);
                text.setSpan(style,i,f,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;

            }
            case 7:
            {
                StyleSpan style=new StyleSpan(Typeface.ITALIC);
                text.setSpan(style,i,f,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            }
            case 8:
            {
                UnderlineSpan underLine=new UnderlineSpan();
                text.setSpan(underLine,i,f,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            }
            case 9:
            {
                BackgroundColorSpan background=new BackgroundColorSpan(currentColor);
                text.setSpan(background,i,f,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            }
            case 10:
            {
                ForegroundColorSpan foreground=new ForegroundColorSpan(currentColor);
                text.setSpan(foreground,i,f,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            }
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int spinnerId=parent.getId();
        if(spinnerId==R.id.fontSpin)
        {
            currentFont=font[position];

        }
        else if(spinnerId==R.id.sizeSpin)
        {
          currentSize=Integer.parseInt(size[position]);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}