package hook;

import net.thucydides.core.model.DataTable;
import net.thucydides.core.model.Story;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.steps.ExecutedStepDescription;
import net.thucydides.core.steps.StepFailure;
import net.thucydides.core.steps.StepListener;

import java.util.Map;

abstract class SerenityCustomListener implements StepListener {

    /**
     * StepListener
     */

    @Override
    public void testSuiteStarted(Class<?> aClass) {
    }

    @Override
    public void testSuiteStarted(Story story) {
    }

    @Override
    public void testSuiteFinished() {
    }

    @Override
    public void testStarted(String s) {
    }

    @Override
    public void testStarted(String s, String s1) {
    }

    @Override
    public void testFinished(TestOutcome testOutcome) {
    }

    @Override
    public void testRetried() {
    }

    @Override
    public void stepStarted(ExecutedStepDescription executedStepDescription) {
    }

    @Override
    public void skippedStepStarted(ExecutedStepDescription executedStepDescription) {
    }

    @Override
    public void stepFailed(StepFailure stepFailure) {
    }

    @Override
    public void lastStepFailed(StepFailure stepFailure) {
    }

    @Override
    public void stepIgnored() {
    }

    @Override
    public void stepPending() {
    }

    @Override
    public void stepPending(String s) {
    }

    @Override
    public void stepFinished() {
    }

    @Override
    public void testFailed(TestOutcome testOutcome, Throwable throwable) {
    }

    @Override
    public void testIgnored() {
    }

    @Override
    public void testSkipped() {
    }

    @Override
    public void testPending() {
    }

    @Override
    public void testIsManual() {
    }

    @Override
    public void notifyScreenChange() {
    }

    @Override
    public void useExamplesFrom(DataTable dataTable) {
    }

    @Override
    public void addNewExamplesFrom(DataTable dataTable) {
    }

    @Override
    public void exampleStarted(Map<String, String> map) {
    }

    @Override
    public void exampleFinished() {
    }

    @Override
    public void assumptionViolated(String s) {
    }

    @Override
    public void testRunFinished() {
    }
}
