package com.openshift.jenkins.plugins.pipeline;

import com.openshift.jenkins.plugins.pipeline.model.GlobalConfig;
import com.openshift.jenkins.plugins.pipeline.model.IOpenShiftDeploymentVerification;
import hudson.Extension;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;
import java.io.IOException;

public class OpenShiftDeploymentVerifier extends TimedOpenShiftBaseStep implements IOpenShiftDeploymentVerification {

    protected final String depCfg;
    protected final String replicaCount;
    protected final String verifyReplicaCount;


    // Fields in config.jelly must match the parameter names in the "DataBoundConstructor"
    @DataBoundConstructor
    public OpenShiftDeploymentVerifier(String apiURL, String depCfg, String namespace, String replicaCount, String authToken, String verbose, String verifyReplicaCount, String waitTime, String waitUnit) {
        super(apiURL, namespace, authToken, verbose, waitTime, waitUnit);
        this.depCfg = depCfg != null ? depCfg.trim() : null;
        this.replicaCount = replicaCount != null ? replicaCount.trim() : null;
        this.verifyReplicaCount = verifyReplicaCount != null ? verifyReplicaCount.trim() : null;
    }

    public String getDepCfg() {
        return depCfg;
    }

    public String getReplicaCount() {
        return replicaCount;
    }

    public String getVerifyReplicaCount() {
        return verifyReplicaCount;
    }

    /**
     * Descriptor for {@link OpenShiftDeploymentVerifier}. Used as a singleton.
     * The class is marked as public so that it can be accessed from views.
     */
    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends TimedBuildStepDescriptor<Builder> {

        public FormValidation doCheckDepCfg(@QueryParameter String value)
                throws IOException, ServletException {
            return ParamVerify.doCheckDepCfg(value);
        }

        public FormValidation doCheckReplicaCount(@QueryParameter String value)
                throws IOException, ServletException {
            return ParamVerify.doCheckReplicaCount(value);
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return DISPLAY_NAME;
        }

        @Override
        protected long getStaticDefaultWaitTime() {
            return GlobalConfig.DEFAULT_DEPLOY_VERIFY_WAIT;
        }

    }

}

