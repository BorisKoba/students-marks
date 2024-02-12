package telran.students.exceptions;

import telran.students.service.ServiceErrorMessages;

@SuppressWarnings("serial")
public class MarkIllegalStateException extends IllegalStateException {
	public MarkIllegalStateException() {
		super(ServiceErrorMessages.MARK_ALREADY_EXIST);
	}
}
