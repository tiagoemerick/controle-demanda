package br.com.bb.controle.arh.infra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;

import br.com.bb.controle.arh.dao.GenericDao;
import br.com.bb.controle.arh.model.IEntity;

@SuppressWarnings({ "unchecked", "static-access" })
public class DataModel<T extends IEntity> extends LazyDataModel<T> implements SelectableDataModel<T>, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@Any
	private GenericDao<IEntity> genericDao;

	private static List<?> list;
	private static Class<? extends IEntity> entityClass;

	public void setList(List<T> list) {
		if (list != null) {
			this.list = list;
		}
		entityClass = (list != null && list.isEmpty()) ? null : list.get(0).getClass();
		super.setWrappedData(list);
	}

	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		List<T> data = new ArrayList<T>();

		if (this.list != null) {
			data = (List<T>) this.list;

			// rowCount
			int dataSize = data.size();
			this.setRowCount(dataSize);

			// paginate
			if (dataSize > pageSize) {
				try {
					return data.subList(first, first + pageSize);
				} catch (IndexOutOfBoundsException e) {
					return data.subList(first, first + (dataSize % pageSize));
				}
			}
		}
		return data;
	}

	@Override
	public T getRowData(String key) {
		return (T) genericDao.find((Class<IEntity>) entityClass, key);
	}

	@Override
	public Object getRowKey(T t) {
		return t.getId();
	}

}
