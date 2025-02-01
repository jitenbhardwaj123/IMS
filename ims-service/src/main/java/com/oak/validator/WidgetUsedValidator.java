package com.oak.validator;

import static java.util.stream.Collectors.joining;

import java.util.List;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.oak.common.validation.constraint.impl.BaseValidator;
import com.oak.entity.PageEntity;
import com.oak.repository.PageRepository;

public class WidgetUsedValidator extends BaseValidator<WidgetUsed, Long> {
	@Autowired
	private PageRepository pageRepository;

	@Override
	public boolean isValid(Long widgetId, ConstraintValidatorContext context) {
		boolean widgetNotUsed = true;
		if (widgetId != null) {
			List<PageEntity> pages = pageRepository.findByWidgetsId(widgetId);
			if (! pages.isEmpty()) {
				widgetNotUsed = false;
				addCustomViolation(context, "widget.delete.used", pages.stream().map(PageEntity::getName).collect(joining(", ")));
			}
		}
		return widgetNotUsed;
	}
}
