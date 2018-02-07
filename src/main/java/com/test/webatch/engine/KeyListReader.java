package com.test.webatch.engine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.test.webatch.dao.BasicDao;
import com.test.webatch.domain.KeyContext;

/**
 * 
 * KeyListReader 首先读取所有的keys,loadKeys() 然后一条条记录处理，loadItem(KEY key)
 * 通过keyIterator来遍历所有的keys
 *
 * @param <KEY>
 * @param <INFO>
 */
public abstract class KeyListReader<KEY, INFO> extends
		AbstractItemCountingItemStreamItemReader<INFO> implements
		ItemStreamReader<INFO>, Partitioner, BeanNameAware {

	private List<KEY> allKeys;

	private Iterator<KEY> keyIterator;

	private final String KEY_CONTEXT_KEY = "keyContextId";

	@Autowired
	private BasicDao<KeyContext> basicDao;

	private int minPartitionSize = 10;

	private int maxPartitionSize = Integer.MAX_VALUE;

	protected abstract List<KEY> loadKeys();

	protected abstract INFO loadItem(KEY key);

	@Override
	protected INFO doRead() throws Exception {
		if (!keyIterator.hasNext())
			return null;
		INFO info = loadItem(keyIterator.next());
		while (info == null) {
			if (!keyIterator.hasNext())
				return null;
			setCurrentItemCount(getCurrentItemCount() + 1);
			info = loadItem(keyIterator.next());
		}
		return info;
	}

	@Override
	protected void doOpen() throws Exception {
		keyIterator = allKeys.iterator();
	}

	@Override
	public void update(ExecutionContext executionContext)
			throws ItemStreamException {
		super.update(executionContext);

	}

	@Override
	protected void jumpToItem(int itemIndex) throws Exception {
		keyIterator = allKeys.subList(itemIndex, allKeys.size()).iterator();
	}

	@Override
	protected void doClose() throws Exception {
		keyIterator = null;
		allKeys = null;
	}

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		List<KEY> keys = loadKeys();

		int total = keys.size();

		int partitionSize = total / gridSize + 1;
		partitionSize = Math.max(partitionSize, minPartitionSize);
		partitionSize = Math.min(partitionSize, maxPartitionSize);

		Map<String, ExecutionContext> result = new TreeMap<String, ExecutionContext>();
		int rest = total;
		int i = 0;
		while (rest > 0) {
			ExecutionContext ec = new ExecutionContext();
			ArrayList<KEY> subList = new ArrayList<KEY>(keys.subList(i
					* partitionSize, Math.min((i + 1) * partitionSize, total))); // 鍔ㄦ?subList涓嶅彲涓茶鍖?

			KeyContext context = createNewKeyContext(subList);

			ec.putLong("keyContextId", context.getContextId());

			result.put(MessageFormat.format("part{0,number,000}", i), ec);
			rest -= partitionSize;
			i++;
		}
		System.out.println("result=" + result.size());

		return result;
	}

	@BeforeStep
	void beforeStep(StepExecution stepExecution) {
		ExecutionContext ec = stepExecution.getExecutionContext();
		if (ec.containsKey(KEY_CONTEXT_KEY)) {
			long contextId = ec.getLong(KEY_CONTEXT_KEY);
			KeyContext param = new KeyContext();
			param.setContextId(Integer.parseInt(String.valueOf(contextId)));
			KeyContext context = basicDao.queryByKey(param);
			allKeys = byteToList(context.getKeyList());
		} else {
			allKeys = loadKeys();
			ec.putLong(KEY_CONTEXT_KEY,
					createNewKeyContext(new ArrayList<KEY>(allKeys))
							.getContextId());
			System.out.println("allKeys size=" + allKeys.size());
		}
	}

	@Transactional
	private KeyContext createNewKeyContext(ArrayList<KEY> subList) {
		KeyContext context = new KeyContext();

		context.setKeyList(listToArray(subList));
		basicDao.save(context);
		return context;
	}

	private byte[] listToArray(ArrayList<KEY> subList) {
		Object[] objs = subList.toArray();
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(byteArray);
			out.writeObject(objs);
		} catch (IOException e) {
			return new byte[0];
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (byteArray != null) {
				try {
					byteArray.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return byteArray.toByteArray();
	}

	@SuppressWarnings("unchecked")
	private List<KEY> byteToList(byte[] buff) {
		ByteArrayInputStream byteArray = new ByteArrayInputStream(buff);
		ObjectInputStream out = null;

		try {
			out = new ObjectInputStream(byteArray);
			Object[] objs = (Object[]) out.readObject();

			return (List<KEY>) Arrays.asList(objs);

		} catch (Exception e) {
			return new ArrayList<KEY>();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					;
				}
			}

			if (byteArray != null) {
				try {
					byteArray.close();
				} catch (IOException e) {
					;
				}
			}
		}
	}

	@Override
	public void setBeanName(String name) {
		setName(name);
	}

	public int getMinPartitionSize() {
		return minPartitionSize;
	}

	public void setMinPartitionSize(int minPartitionSize) {
		this.minPartitionSize = minPartitionSize;
	}

	public int getMaxPartitionSize() {
		return maxPartitionSize;
	}

	public void setMaxPartitionSize(int maxPartitionSize) {
		this.maxPartitionSize = maxPartitionSize;
	}

}
