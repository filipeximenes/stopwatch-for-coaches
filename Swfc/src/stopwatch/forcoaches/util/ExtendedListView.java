package stopwatch.forcoaches.util;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//ATENÇÃO: initialize() deve ser sempre chamado antes de se começar a usar esta classe

public class ExtendedListView<T> extends ListView{
	private ArrayAdapter<T> arrayAdapter;
	

	public ExtendedListView(Context context) {
		super(context);
	}
	
	public ExtendedListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public ExtendedListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void initialize(){
		arrayAdapter = new ArrayAdapter<T>(getContext(), getId()){
			public View getView(int position, View convertView, ViewGroup parent) {
				return adapterGetView(position, convertView, parent);
			}
		};
		
		init();
		
		setAdapter(arrayAdapter);
	}
	
	protected void init(){
		
	}
	
	protected View adapterGetView(int position, View convertView, ViewGroup parent) {
		return null;
	}
	
	protected View setHeaderView(){
		return null;
	}
	
	public void adapterAdd(T object){
		arrayAdapter.add(object);
	}
	
	public T adapterGetItem(int position){
		return arrayAdapter.getItem(position);
	}
	
	public void adapterNotifyDataSetChanged(){
		arrayAdapter.notifyDataSetChanged();
	}
	
	public boolean adapterIsEmpty(){
		return arrayAdapter.isEmpty();
	}
	
	public int adapterGetCount(){
		return arrayAdapter.getCount();
	}
	
	public void adapterClear(){
		arrayAdapter.clear();
	}
	
	public void adapterRemove(T object){
		arrayAdapter.remove(object);
	}
	
	public final void adapterAddAll(List<? extends T> list) {
		for (int i = 0; i < list.size(); i++){
			arrayAdapter.add(list.get(i));
		}
	}
}
