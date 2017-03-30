# Seisir
*Real-time seizure monitoring using the Microsoft band*

Winning project at HackHarvard2015, in the category of official sponsor Microsoft-API

### Inspiration
We believe that Seisir as a prototype demonstrates the life-saving potential of wearable technology in the health sciences. With the eyes put on the unpredictability and despair of epileptic seizures, our team was driven to create an intelligent design that is able to respond timely to emergency situations.
Our algorithm uses a wrist-worn biosensor to provide a convulsive seizure alarm system for infants or secluded elders, as well as objective quantification of seizure frequency for medical records.

### What it does
Seisir works in real time, pulling data from an array of sensors in the Microsoft Band 2, and pushing it to a secure server. We use Microsoft Azure’s API to perform constant data analysis over the cloud.
Our anomaly detection algorithm is triggered by SNS-mediated Electrodermal Activity, an autonomic change indicative of seizure activity. Following this activation, we fuse a geometric model of the 3D posture of a stiffened arm with gyroscopic data from the user's wrist, using relatively brief runs of MCMC. We then analyze the vibrational movements and muscle spasms in the person's arm and wrist (with FFT), and furnish these features to a Convolutional Neural Network (CNN) that searches for correspondences with tonic-clonic seizure activity.
In case of positive feedback, an alert system is implemented to contact one's family members and medical staff within the shortest conceivable alarm period ever.

### What's next for Seisir—Epilepsy Monitoring App
It is our hope that the technology behind Seisir has the opportunity to mature with access to clinical data and user testing, and to become quickly accessible to the masses with the advent of IoT. We have been especially excited by the utility of 24/7 monitoring data for medical purposes, and are interested in the refinement of our model to categorize different types of seizures.

### References
1. Poh, M. Z., Loddenkemper, T., Reinsberger, C., Swenson, N. C., Goyal, S., Sabtala, M. C., Madsen, J. R. and Picard, R. W. (2012), **Convulsive seizure detection using a wrist-worn electrodermal activity and accelerometry biosensor.** *Epilepsia*, 53: e93–e97. doi: 10.1111/j.1528-1167.2012.03444.x
2. Sarkis, R. A., Thome-Souza, S., Poh, M.-Z., Llewellyn, N., Klehm, J., Madsen, J. R., … Reinsberger, C. (2015). **Autonomic changes following generalized tonic clonic seizures: An analysis of adult and pediatric patients with epilepsy**. *Epilepsy Research*(115), 113-118. doi:10.1016/j.eplepsyres.2015.06.005
3. Shen, S., Wang H., Choudhury, R. R. (2016) **I am a Smartwatch and I can Track my User’s Arm**. In _MobiSys '16 Proceedings of the 14th Annual International Conference on Mobile Systems, Applications, and Services_ (pp. 85-96). Singapore, Singapore.