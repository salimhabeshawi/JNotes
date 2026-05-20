ifeq ($(OS),Windows_NT)
RUN_CMD = run.bat
else
RUN_CMD = run.sh
endif

.PHONY: run
.PHONY: _run_unix
_run_unix:
	@bash $(RUN_CMD)

.PHONY: _run_windows
_run_windows:
	@cmd /c $(RUN_CMD)

run:;
	@echo "Running JNotes via $(RUN_CMD)"

ifeq ($(OS),Windows_NT)
run: _run_windows
else
run: _run_unix
endif
