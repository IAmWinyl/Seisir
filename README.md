# Seisir
*Real-time seizure monitoring using the Microsoft band*

Winning project at HackHarvard2015, in the category of official sponsor Microsoft-API

### Inspiration
We believe that Seisir as a prototype demonstrates the life-saving potential of wearable technology in the health sciences. With the eyes put on the unpredictability and despair of epileptic seizures, our team was driven to create an intelligent design that is able to respond timely to emergency situations.
Our algorithm uses a wrist-worn biosensor to provide a convulsive seizure alarm system for infants or secluded elders, and objective quantification of seizure frequency.

### What it does
Seisir works in real time, pulling data from an array of sensors in the Microsoft Band 2, and pushing it to a secure server. We use Microsoft Azure’s API to perform constant data analysis over the cloud.
Our anomaly detection algorithm executes an FFT in parsed data from the accelerometer, to observe the vibrational movements and muscle spasms in the user’s wrist (indicative of clonic seizure activity). The data is then furnished to a trained CNN (Convolutional Neural Network), complemented with SNS-mediated Electrodermal Activity.
In case of positive feedback, an alert system is implemented to contact one's family members and medical staff within seconds.

### What's next for Seisir—Epilepsy Monitoring App
It is our hope that the technology behind Seisir has the opportunity to mature with access to clinical data and user testing, and to become quickly accessible to the masses with the advent of IoT. Lately, we have been especially excited by innovations in wearable technology paradigms as well as new research that opens horizons to enhance our seizure prediction model.
In algorithmic terms, our first step will be to reduce the need of constant accelerometry monitoring, by defining an activation threshold in EDA.

### References
- Poh, M. Z., Loddenkemper, T., Reinsberger, C., Swenson, N. C., Goyal, S., Sabtala, M. C., Madsen, J. R. and Picard, R. W. (2012), **Convulsive seizure detection using a wrist-worn electrodermal activity and accelerometry biosensor.** *Epilepsia*, 53: e93–e97. doi: 10.1111/j.1528-1167.2012.03444.x
- Sarkis, R. A., Thome-Souza, S., Poh, M.-Z., Llewellyn, N., Klehm, J., Madsen, J. R., … Reinsberger, C. (2015). **Autonomic changes following generalized tonic clonic seizures: An analysis of adult and pediatric patients with epilepsy**. *Epilepsy Research*(115), 113-118. doi:10.1016/j.eplepsyres.2015.06.005