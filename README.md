FragmentOrientedApplication
===========================

<strong>Structure of Sample App</strong>

This sample application has following notable components:
<ul>
	<li><strong style="color: #000000; font-style: normal;">Home</strong> - This is the only activity, hosts a navigation drawer, which is the global UI for the app, and a FrameLayout that hosts fragments. Integrates facebook session. Implements HomeInterface.</li>
	<li><strong>HomeInterface</strong> - This is a clubbed interface that extends all the interfaces that needs to be implemented in Home by various fragments (related to inter-fragment communication).</li>
	<li><strong>BaseFragment </strong>-Its an abstract base class for all the fragments. This class does a lot of backing jobs, like provisioning for back press handling.</li>
	<li><strong>DrawerItemBaseFragment </strong>-Its an abstract base class for all drawer fragments. It extends BaseFragment. Fragments extending this would correspond to a section in Navigation Drawer.</li>
</ul>
In Home, we always have a reference to currently visible fragment and current drawer fragment, which is being revised respectively from these two abstract classes. Following references do this:
<code>BaseFragment selectedFragment;</code>
<code>DrawerItemBaseFragment selectedDrawerItemFragment;</code>
