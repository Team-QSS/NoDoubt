package qss.nodoubt.input;

public interface ScrollListener {

	/**
	 * 스크롤 되었을때 나오는 이벤트
	 * @param offset 스크롤 된 정도
	 */
	void invoke(double offset);
}
