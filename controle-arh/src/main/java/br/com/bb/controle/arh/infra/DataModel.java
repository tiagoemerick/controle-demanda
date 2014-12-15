package br.com.bb.controle.arh.infra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;

import br.com.bb.controle.arh.dao.GenericDao;
import br.com.bb.controle.arh.model.IEntity;
import br.com.bb.controle.arh.util.PropertyComparator;
import br.com.bb.controle.arh.util.ReflectionUtil;

@SuppressWarnings({ "unchecked" })
public class DataModel<T extends IEntity> extends LazyDataModel<T> implements SelectableDataModel<T>, Serializable {

	private static final long serialVersionUID = 1L;

	@Any
	@Inject
	private GenericDao<IEntity> genericDao;

	private List<?> list;
	private Class<? extends IEntity> entityClass;

	public void setList(List<T> list) {
		if (list != null) {
			this.list = list;
		}
		entityClass = (list != null && list.isEmpty()) ? null : list.get(0).getClass();
		super.setWrappedData(list);
	}

	public void removeFromList(Object obj) {
		if (list != null) {
			this.list.remove(obj);
		}
		super.setWrappedData(list);
	}

	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		List<T> data = new ArrayList<T>();

		if (this.list != null) {
			// filtros
			for (T car : (List<T>) this.list) {
				boolean match = true;

				if (filters != null) {
					for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
						try {
							String filterProperty = it.next();
							Object filterValue = filters.get(filterProperty);
							String fieldValue = String.valueOf(ReflectionUtil.getFieldValue(car, filterProperty));

							if (filterValue == null || fieldValue.toLowerCase().contains(filterValue.toString().toLowerCase())) {
								match = true;
							} else {
								match = false;
								break;
							}
						} catch (Exception e) {
							match = false;
						}
					}
				}
				if (match) {
					data.add(car);
				}
			}

			// ordenacao
			if (sortField != null) {
				boolean ASCENDING = (sortOrder.ordinal() == 0 ? true : false);
				Collections.sort(data, new PropertyComparator<T>(sortField, ASCENDING));
			}

			int dataSize = data.size();
			this.setRowCount(dataSize);

			// paginacao
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
